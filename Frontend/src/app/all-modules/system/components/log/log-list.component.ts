import { DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { ToastrService } from 'ngx-toastr';
import { LoginService } from 'src/app/login/services/login.services';
import { environment } from 'src/environments/environment';
import { SystemService } from '../../service/system.service';

declare const $: any;
@Component({
  selector: 'app-log-list',
  templateUrl: './log-list.component.html',
  styleUrls: ['./log-list.component.css']
})
export class LogListComponent implements OnInit {

  public baseUrl = environment.baseUrl;
  private polling: any;

  public pipe = new DatePipe('en-US');
  public myFromGroup: FormGroup;

  public configPgn: any;
  public listData: any = [];
  public editId: any;
  public tempId: any;

   // search fields for
   private code: string;
   private username:string;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private spinnerService: NgxSpinnerService,
    private toastr:ToastrService,
    private systemService:SystemService,
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
    this._getListData();

    $('body').addClass('mini-sidebar');
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
    const apiURL = this.baseUrl + '/visitor/getList';

    let queryParams: any = {};
   // this.creationUser = this.loginService.getUser().username;
    const params = this.getUserQueryParams(this.configPgn.pageNum, this.configPgn.pageSize);
    queryParams = params;

    queryParams.rEntityName = 'Visitor';
    queryParams.rReqType = 'getListData';


   // this.spinnerService.show();
    this.systemService.sendGetRequest(apiURL, queryParams).subscribe(
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
    if(this.username){
      params[`username`] = this.username;
    }


    return params;

  }

  searchByUsername(val){
    this.username = val;
   // this._getListData();
  }
  searchBySearchButton(){
    this._getListData();
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


}
