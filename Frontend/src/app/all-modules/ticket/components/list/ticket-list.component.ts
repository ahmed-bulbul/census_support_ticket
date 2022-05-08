import { id } from './../../../../../assets/all-modules-data/id';
import { DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { ToastrService } from 'ngx-toastr';
import { environment } from 'src/environments/environment';
import { TicketService } from '../../service/ticket.service';
import { LoginService } from 'src/app/login/services/login.services';

declare const $: any;
@Component({
  selector: 'app-ticket-list',
  templateUrl: './ticket-list.component.html',
  styleUrls: ['./ticket-list.component.css']
})
export class TicketListComponent implements OnInit {

  public baseUrl = environment.baseUrl;
  private polling: any;

  public pipe = new DatePipe('en-US');
  public myFromGroup: FormGroup;

  public configPgn: any;
  public listData: any = [];
  public editId: any;
  public tempId: any;
  // Action auth for user
  public authObj: any = {
    create: false,
    read: false,
    update: false,
    delete: false
  };

  // search fields for
  private code: string;
  private creationUser: string;
  private status : string;
  private tabletSerialNo: string;

  //highlight the row
  public highlight:boolean = false;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private spinnerService: NgxSpinnerService,
    private toastr:ToastrService,
    private ticketService:TicketService,
    private loginService:LoginService
  ) {

    this.configPgn = {
      // my props
      pageNum: 1,
      pageSize: 10,
      totalItem: 50,
      pageSizes: [3, 5, 10, 25, 50, 100, 200, 500, 1000],
      pgnDiplayLastSeq: 10,
      // ngx plugin props
      itemsPerPage: 10,
      currentPage: 1,
      totalItems: 50
    };
  }

  ngOnInit(): void {

     // set init params
     this.myFromGroup = new FormGroup({
      pageSize: new FormControl()
    });
    this.myFromGroup.get('pageSize').setValue(this.configPgn.pageSize);

    // bind event & action
    this._bindFromFloatingLabel();
    this.pollData();
    this._getListData();

    $('body').addClass('mini-sidebar');


  }

  pollData () {
    this.polling = setInterval(() => {
      this._getListData();


    },2000);
  }

  searchByCode(val) {
    this.code = val;
  }
  searchBySearchButton(){
    this._getListData();
  }
  searchByStatus(val){

    this.status = val;
    this._getListData();

    if(val!==''){
      //destroy ng on init
      this.ngOnDestroy();
    }else{
      this.pollData();
    }


  }

  searchByTabSerialNo(val){
    this.spinnerService.show();
      // if matches highlight the row
      this.tabletSerialNo = val;
      if(val.length > 0){
        this.highlight = true;
      }else{
        this.highlight = false;
      }
      this._getListData();
      this.spinnerService.hide();

  }

  clearFilter(){
    this.code = '';
    $('.filter-row').find('input, select, textarea').val('');
    this._getListData();
    this.highlight = false;
  }

  deleteEntityData(id){

    const apiURL = this.baseUrl + '/ticket/bbs/delete/' + id;
    console.log(apiURL);

    const formData: any = {};
    formData.rEntityName = 'User';
    formData.rActiveOperation = 'delete';

    this.spinnerService.show();
    this.ticketService.sendDeleteRequest(apiURL, formData).subscribe(
      (response: any) => {

        if(response.status === true){
          console.log(response);
          this.spinnerService.hide();
          $('#delete_entity').modal('hide');
          this.toastr.success(response.message, 'Success');
          this._getListData();
        }else{
          this.spinnerService.hide();
          $('#delete_entity').modal('hide');
          this.toastr.info(response.message, 'Info');
        }

      },
      (error) => {
        console.log(error);
        this.spinnerService.hide();
      }
    );

  }

  _bindFromFloatingLabel(){

    const self = this;
    // for floating label
    if ($('.floating').length > 0) {
      $('.floating')
        .on('focus blur', function (e) {
          $(this)
            .parents('.form-focus')
            .toggleClass('focused', e.type === 'focus' || this.value.length > 0);
        })
        .trigger('blur');
    }

    // tslint:disable-next-line:only-arrow-functions
    $('.filter-row').find('input, select, textarea').keyup(function(e){

      console.log(e.keyCode)
      if(e.keyCode === 13){
        self._getSearchData();
      }

    });

  }

  public _getSearchData() {
    this._getListData();

  }

  _getListData(){
    const apiURL = this.baseUrl + '/ticket/bbs/getList';

    let queryParams: any = {};
    this.creationUser = this.loginService.getUser().username;
    const params = this.getUserQueryParams(this.configPgn.pageNum, this.configPgn.pageSize);
    queryParams = params;

    queryParams.rEntityName = 'Ticket';
    queryParams.rReqType = 'getListData';


   // this.spinnerService.show();
    this.ticketService.sendGetRequest(apiURL, queryParams).subscribe(
      (response: any) => {
        if(response.status === true){
          this.listData = response.data;
          this.configPgn.totalItem = response.totalItems;
          this.configPgn.totalItems = response.totalItems;
          this.setDisplayLastSequence();
        //  this.spinnerService.hide();
        }else{
          this.spinnerService.hide();
          this.toastr.info(response.message, 'Info');
        }

      },
      (error) => {
        console.log(error)
      }
    );

  }
  private getUserQueryParams(page: number, pageSize: number): any {

    const params: any = {};

    if (page) {
      params[`pageNum`] = page - 0;
    }
    if (pageSize) {
      params[`pageSize`] = pageSize;
    }

    // push other attributes
    if(this.code){
      params[`code`] = this.code;
    }
    if(this.creationUser){
      params['creationUser']= this.creationUser;
    }
    if(this.status){
      params['status']= this.status;
    }
    if(this.tabletSerialNo){
      params['tabletSerialNo']= this.tabletSerialNo;
    }


    return params;

  }

  // pagination handling methods start -----------------------------------------------------------------------
  setDisplayLastSequence(){
    this.configPgn.pngDiplayLastSeq = (((this.configPgn.pageNum - 1 ) * this.configPgn.pageSize) + this.configPgn.pageSize);
    if(this.listData.length < this.configPgn.pageSize){
      this.configPgn.pngDiplayLastSeq = (((this.configPgn.pageNum - 1 ) * this.configPgn.pageSize) + this.configPgn.pageSize);
    }
    if(this.configPgn.totalItem < this.configPgn.pngDiplayLastSeq){
      this.configPgn.pngDiplayLastSeq = this.configPgn.totalItem;
    }
  }
  handlePageChange(event: number){
    this.configPgn.pageNum = event;
    // set for ngx
    this.configPgn.currentPage = this.configPgn.pageNum;
    this._getListData();
  }
  handlePageSizeChange(event: any): void {
    this.configPgn.pageSize = event.target.value;
    this.configPgn.pageNum = 1;
    // set for ngx
    this.configPgn.itemsPerPage = this.configPgn.pageSize;
    this._getListData();
  }
  // pagination handling methods end -------------------------------------------------------------------------

  ngOnDestroy() {
    clearInterval(this.polling);
    $('body').removeClass('mini-sidebar');

  }

}
