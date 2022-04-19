
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { environment } from 'src/environments/environment';
import { DatePipe } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { ToastrService } from 'ngx-toastr';
import { SystemService } from '../../../service/system.service';

@Component({
  selector: 'app-menu-edit',
  templateUrl: './menu-edit.component.html',
  styleUrls: ['./menu-edit.component.css']
})
export class MenuEditComponent implements OnInit {


  public baseUrl = environment.baseUrl;
  public myForm: FormGroup;

  public isSubmitted = false;
  public myFormData: any = {};
  public parentMenu: any = [];

  constructor(
    private formBuilder: FormBuilder,
    private datePipe: DatePipe,
    private route: ActivatedRoute,
    private router: Router,
    private spinnerService: NgxSpinnerService,
    private systemService:SystemService,
    private toastr:ToastrService
  ) { }

  ngOnInit(): void {
    this._initForm();
    this._getParentMenu();
    this._getFormData();
  }


  _initForm(){

    this.myForm = this.formBuilder.group({
      id: [''],
      code: ['', [Validators.required,Validators.minLength(3)]],
      description: ['', [Validators.required]],
      parentMenuId: [''],
      organizationId:[''],
      openUrl:['',[Validators.required]],
      iconHtml:[''],
      hasChild:[''],
      visibleToAll:[''],
      leftSideMenu:[''],
      isChild:[''],
      isActive:[''],
      sequence:['',[Validators.required]],
    });

  }

  _getFormData(){

    const id =  this.route.snapshot.params.id;
    const apiURL = this.baseUrl + '/system/systemMenu/get/' + id;

    const queryParams: any = {};
    queryParams.rEntityName = 'SystemMenu';
    queryParams.rActiveOperation = 'update';

    this.spinnerService.show();
    this.systemService.sendGetRequest(apiURL, queryParams).subscribe(
      (response: any) => {
        if(response.status === true){
          const data = response.data;
          console.log(data);
          this.myFormData = data;
          this.spinnerService.hide();
          data.organizationId =data.organizationId ? data.organizationId : '';
          data.parentMenuId =data.parentMenuId ? data.parentMenuId : '';

          this.myForm.patchValue(data);
        }else{
          this.spinnerService.hide();
          this.toastr.error(response.message, 'Error');
        }
      },
      (error) => {
        console.log(error)
      }
    );
  }

  _getParentMenu(){
    const apiURL = this.baseUrl + '/common/getParentMenu';
    const queryParams: any = {};
    this.systemService.sendGetRequest(apiURL,queryParams).subscribe((response: any) => {
      if(response.status === true){
        this.parentMenu = response.data;
        console.log(this.parentMenu);
      }else{
        this.toastr.error(response.message, 'Error');
      }
    });
  }


  myFormSubmit(){
    const apiURL = this.baseUrl + '/system/systemMenu/update';
    let formData: any = {};
    formData = this.myForm.value
    console.log(formData);
    this.spinnerService.show();
    this.systemService.sendPutRequest(apiURL,formData).subscribe((response: any) => {
      if(response.status === true){
        this.spinnerService.hide();
        this.toastr.success(response.message, 'Success');
        this.router.navigate(['/system/menu/list']);
      }else{
        this.spinnerService.hide();
        this.toastr.error(response.message, 'Error');
      }
    });
  }

  resetFormValues(){

  }

  get f() {
    return this.myForm.controls;
  }


}
