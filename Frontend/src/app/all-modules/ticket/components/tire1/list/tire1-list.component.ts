import { DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { ToastrService } from 'ngx-toastr';
import { LoginService } from 'src/app/login/services/login.services';
import { environment } from 'src/environments/environment';
import { TicketService } from '../../../service/ticket.service';

@Component({
  selector: 'app-tire1-list',
  templateUrl: './tire1-list.component.html',
  styleUrls: ['./tire1-list.component.css']
})
export class Tire1ListComponent implements OnInit {
  public baseUrl = environment.baseUrl;

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
  private problemCategory:string;
  private receivedFromT1:string;
  private status:string;

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
    this._getListData();
    //this.refreshData()
  }

  _getListData(){

    const apiURL = this.baseUrl + '/ticket/tire1/getList';

    let queryParams: any = {};
    this.problemCategory = "TECHNICAL";
    this.status="OPEN";
   // this.receivedFromT1=this.loginService.getUser().username;
    const params = this.getUserQueryParams(this.configPgn.pageNum, this.configPgn.pageSize);
    queryParams = params;

    queryParams.rEntityName = 'Ticket';
    queryParams.rReqType = 'getListData';



    this.spinnerService.show();
    this.ticketService.sendGetRequest(apiURL, queryParams).subscribe(
      (response: any) => {
        if(response.status === true){
          this.listData = response.data;
          this.configPgn.totalItem = response.totalItems;
          this.configPgn.totalItems = response.totalItems;
          this.setDisplayLastSequence();
          this.spinnerService.hide();
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
  refreshData(){
    this.listData =
      setInterval(() => {
        this._getListData();

      }, 6000);
  }


  searchByCode(val) {
    this.code = val;
  }
  searchBySearchButton(){
    this._getListData();
  }
  clearFilter(){
    this.code = '';
    $('.filter-row').find('input, select, textarea').val('');
    this._getListData();
  }


  receive(id)
  {
    const apiURL = this.baseUrl + '/ticket/tire1/stsUpdate/' + id;
    console.log(apiURL);

    const formData: any = {};

    this.spinnerService.show();
    this.ticketService.sendStstusUpdateRequest(apiURL, formData).subscribe(
      (response: any) => {

        if(response.status === true){
          console.log(response);
          this.spinnerService.hide();
          this.toastr.success(response.message, 'Success');
          this._getListData();
        }else{
          this.spinnerService.hide();
          this.toastr.info(response.message, 'Info');
        }

      },
      (error) => {
        console.log(error);
        this.spinnerService.hide();
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
    if(this.problemCategory)
    {
      params['problemCategory']= this.problemCategory;
    }
    if(this.problemCategory)
    {
      params['status']= this.status;
    }
    if(this.receivedFromT1)
    {
      params['receivedFromT1']= this.receivedFromT1;
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


}
