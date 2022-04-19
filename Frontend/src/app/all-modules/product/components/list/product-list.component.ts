import { DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { ToastrService } from 'ngx-toastr';
import { LoginService } from 'src/app/login/services/login.services';
import { environment } from 'src/environments/environment';
import { ProductService } from '../../service/product.service';


declare const $: any;
@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.css']
})
export class ProductListComponent implements OnInit {

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
    private srcName: string;
    private userId: string;


  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private spinnerService: NgxSpinnerService,
    private toastr:ToastrService,
    private productService: ProductService,
    private loginService:LoginService,
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

    let apiURL = this.baseUrl + "/product/getLists";
    const user = this.loginService.getUser();
    this.userId=user.id;

    let queryParams: any = {};
    const params = this._getUserQueryParams(this.configPgn.pageNum, this.configPgn.pageSize);
    queryParams = params;

    this.spinnerService.show();
    this.productService.sendGetRequest(apiURL, queryParams).subscribe(
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

  private _getUserQueryParams(page: number, pageSize: number): any {

    let params: any = {};

    if (page) {
      params[`pageNum`] = page - 0;
    }
    if (pageSize) {
      params[`pageSize`] = pageSize;
    }

    // push other attributes
    if(this.srcName){
      params[`name`] = this.srcName;
    }
    if(this.userId){
      params[`userId`] = this.userId;
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

  searchByName(val) {
    this.srcName =val;
  }


  searchBySearchButton() {
    this._getListData();
  }

  deleteEnityData(id){
    let apiURL = this.baseUrl + "/product/delete/" + id;
    console.log(apiURL);

    let formData: any = {};
    formData.rEntityName = "Product";
    formData.rActiveOperation = "delete";

    this.spinnerService.show();
    this.productService.sendDeleteRequest(apiURL, formData).subscribe(
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

}
