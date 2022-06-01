import { UserService } from './../../service/user.service';


import { Component, OnInit, AfterViewInit, OnDestroy } from '@angular/core';
import { DatePipe } from "@angular/common";
import { ActivatedRoute, Router } from "@angular/router";
import { ToastrService } from "ngx-toastr";
import { FormGroup, FormControl } from '@angular/forms'
import { NgxSpinnerService } from 'ngx-spinner';
import { environment } from 'src/environments/environment';

declare const $: any;
@Component({
  selector: 'app-users-list',
  templateUrl: './users-list.component.html',
  styleUrls: ['./users-list.component.css']
})
export class UsersListComponent implements OnInit {


  // cores
  public baseUrl = environment.baseUrl;

  public pipe = new DatePipe("en-US");
  public myFromGroup: FormGroup;

  public editId: any;
  public tempId: any;
  // list
  public listData: any = [];
  public configPgn: any;

    // search fields
    private srcFromDate: string;
    private srcToDate: string;
    private srcUsername: string;


  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private spinnerService: NgxSpinnerService,
    private toastr:ToastrService,
    private UserService: UserService,
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


  }

  private _bindFromFloatingLabel(){
    var self = this;
    // for floating label
    if ($(".floating").length > 0) {
      $(".floating")
        .on("focus blur", function (e) {
          $(this)
            .parents(".form-focus")
            .toggleClass("focused", e.type === "focus" || this.value.length > 0);
        })
        .trigger("blur");
    }

    $('.filter-row').find('input, select, textarea').keyup(function(e){

      console.log(e.keyCode)
      if(e.keyCode == 13){
        self._getSearchData();
      }

    });

  }

  public _getSearchData() {
    this._getListData();
  }

  private _getListData() {

    let apiURL = this.baseUrl + "/acl/user/getUserList";

    let queryParams: any = {};
    const params = this.getUserQueryParams(this.configPgn.pageNum, this.configPgn.pageSize);
    queryParams = params;

    this.spinnerService.show();
    this.UserService.sendGetRequest(apiURL, queryParams).subscribe(
      (response: any) => {
        if(response.status == true){
          this.listData = response.data;
          this.configPgn.totalItem = response.totalItems;
          this.configPgn.totalItems = response.totalItems;
          this.setDisplayLastSequence();
          this.spinnerService.hide();
        }else{
          this.spinnerService.hide();
          this.toastr.info(response.message, "Info");
        }

      },
      (error) => {
        console.log(error)
      }
    );

  }

  deleteEnityData(dataId){
    let apiURL = this.baseUrl + "/acl/user/delete/" + dataId;
    console.log(apiURL);

    let formData: any = {};
    formData.rEntityName = "User";
    formData.rActiveOperation = "delete";

    this.spinnerService.show();
    this.UserService.sendDeleteRequest(apiURL, formData).subscribe(
      (response: any) => {

        if(response.status == true){
          console.log(response);
          this.spinnerService.hide();
          $("#delete_entity").modal("hide");
          this.toastr.success(response.message, "Success");
          this._getListData();
        }else{
          this.spinnerService.hide();
          $("#delete_entity").modal("hide");
          this.toastr.info(response.message, "Info");
        }

      },
      (error) => {
        console.log(error);
        this.spinnerService.hide();
      }
    );
  }

  private getUserQueryParams(page: number, pageSize: number): any {

    let params: any = {};

    if (page) {
      params[`pageNum`] = page - 0;
    }
    if (pageSize) {
      params[`pageSize`] = pageSize;
    }

    // push other attributes
    if(this.srcUsername){
      params[`username`] = this.srcUsername;
    }
    if(this.srcFromDate && this.srcToDate){
      params[`fromDate`] = this.srcFromDate;
      params[`toDate`] = this.srcToDate;
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

  searchByUsername(val) {
    this.srcUsername =val;
  }

  searchByFromDate(event) {}

  searchByToDate(event) {}

  searchBySearchButton() {
    this._getListData();
  }

}
