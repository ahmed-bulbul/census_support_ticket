import { DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { ToastrService } from 'ngx-toastr';
import { environment } from 'src/environments/environment';
import { TicketService } from '../../service/ticket.service';

declare const $: any;
@Component({
  selector: 'app-ticket-create',
  templateUrl: './ticket-create.component.html',
  styleUrls: ['./ticket-create.component.css']
})
export class TicketCreateComponent implements OnInit {

  public baseUrl = environment.baseUrl;
  public myForm: FormGroup;
  public parentMenu: any = [];
  public isSubmitted = false;

  // search fields for
  private simNo: string;
  private barCode:string;

  //enabled search option
  public searchByBarCode:boolean = true;
  public searchBySimNo:boolean = false;



  constructor(
    private formBuilder: FormBuilder,
    private datePipe: DatePipe,
    private route: ActivatedRoute,
    private router: Router,
    private spinnerService: NgxSpinnerService,
    private toastr:ToastrService,
    private ticketService:TicketService
  ) { }

  ngOnInit(): void {
    this._initForm();
  }

  _initForm(){

      this.myForm = this.formBuilder.group({
        id: [''],
        deviceUserPhone: ['', [Validators.required,Validators.maxLength(11),Validators.pattern('^[0-9]*$')]],
        devicePhone: ['', [Validators.maxLength(13),Validators.pattern('^[0-9]*$')]],
        deviceUserId: [''],
        tabletSerialNo: ['', [Validators.required]],
        imeiNo: ['', [Validators.required]],
        imeiNo2: ['', [Validators.required]],
        problemCategory: ['', [Validators.required]],
        problemType: [''],
        problemDescription:['', [Validators.required]],
        code: [''],
        createdBy:[''],
        status:[''],
        holdTime:[''],
        solutionType:[''],
        solutionDescription:[''],
        solvedBy:[''],
      });

  }

  myFormSubmit(){

    const apiURL = this.baseUrl + '/ticket/bbs/create';
    this.isSubmitted = true;
    if(this.myForm.invalid){
      return;
    }

    let formData: any;
    formData = Object.assign(this.myForm.value,{
      createdBy: this._getCreatedUser().value ? {id: this._getCreatedUser().value} : null,
      solvedBy: this._getSolvedByUser().value ? {id: this._getSolvedByUser().value} : null,

    });
    formData.status='OPEN';
    formData.rEntityName = 'Ticket';
    formData.rActiveOperation = 'Create';

    this.spinnerService.show();
    this.ticketService.sendPostRequest(apiURL, formData).subscribe(
      (response: any) => {
        if(response.status === true){
          console.log(response);
          this.spinnerService.hide().then(r => console.log('spinner stopped'));
          this.toastr.success('Ticket created successfully', 'Success', { positionClass:'toast-custom' });
          this.router.navigate(['/ticket/bbs/list'], {relativeTo: this.route}).then(r => console.log('navigated'));
        }else{
          this.spinnerService.hide().then(r => console.log('spinner stopped'));
          this.toastr.error(response.message, 'Error');
        }
      },
      (error) => {
        console.log(error.message);
        this.toastr.show(error.error.message, 'Show');
        this.spinnerService.hide().then(r => console.log('spinner stopped'));
      }
    );


  }

  get f() { return this.myForm.controls; }
  resetFormValues(){
    //reset form
    this.myForm.reset();
  }

  _getCreatedUser(){
    return this.myForm.get('createdBy');
  }
  _getSolvedByUser(){
    return this.myForm.get('solvedBy');
  }

  searchBySimNoOrSnNo(val){
    //if val not start with 88 then add 88
    if(val.length>0){
      if(val.substr(0,2)!=='88'){
        val = '88'+val;
      }
      this.simNo = val;
      this.barCode='';
    }



  }
  searchByBarCodeNo(val){
    if(val.length>0){
      this.barCode = val;
      this.simNo='';
    }
  }

  search(){
    if(this.simNo.length>0){
      this._searchBySimNoOrBarCode("simNo");
    }
    if(this.barCode.length>0){
      this._searchBySimNoOrBarCode("barCode");
    }
  }

  _searchBySimNoOrBarCode(type){

    let apiURL;

    if(type==='simNo'){
      apiURL= this.baseUrl + '/searchTabletBySimNo';
    }else{
      apiURL= this.baseUrl + '/searchTabletByBarCode';
    }

    this.spinnerService.show();

    let queryParams: any = {};
    const params = this.getUserQueryParams();
    queryParams = params;

    this.ticketService.sendGetRequest(apiURL,queryParams).subscribe(
      (response: any) => {
        if(response.status === true){
          console.log(response);
          this.spinnerService.hide().then(r => console.log('spinner stopped'));
          this.myForm.patchValue({
            devicePhone: response.data.simNo,
            tabletSerialNo: response.data.barCode,
            imeiNo: response.data.imei1,
            imeiNo2: response.data.imei2,
          });
          // form are readonly now
          $('#devicePhone').attr('readonly', true);
          $('#imeiNo2').attr('readonly', true);
          $('#imeiNo').attr('readonly', true);
          $('#tabletSerialNo').attr('readonly', true);

        }else if(response.status === false){
          this.spinnerService.hide().then(r => console.log('spinner stopped'));
          //reset form
          $('#devicePhone').attr('readonly', false);
          $('#imeiNo2').attr('readonly', false);
          $('#imeiNo').attr('readonly', false);
          $('#tabletSerialNo').attr('readonly', false);
          this.myForm.reset();
          this.toastr.info(response.message, 'Info');
        }
        else{
          this.spinnerService.hide().then(r => console.log('spinner stopped'));
          this.toastr.error(response.message, 'Error');
        }
      },
      (error) => {
        console.log(error.message);
        this.toastr.show(error.error.message, 'Show');
        this.spinnerService.hide().then(r => console.log('spinner stopped'));
      }
    );

  }

  onChangeTicketType(val){
    if(val==='BARCODE'){
      this.searchByBarCode = true;
      this.searchBySimNo = false;
    }
    else if(val==='SIMNO'){
      this.searchBySimNo = true;
      this.searchByBarCode = false;
    }
  }

  private getUserQueryParams(): any {

    const params: any = {};
    // push other attributes
    if(this.simNo){
      params[`simNo`] = this.simNo;
    }

    if(this.barCode){
      params[`barCode`] = this.barCode;
    }

    return params;

  }

}
