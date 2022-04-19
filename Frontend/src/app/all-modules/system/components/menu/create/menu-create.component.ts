
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { ToastrService } from 'ngx-toastr';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { environment } from 'src/environments/environment';
import { DatePipe } from '@angular/common';
import { SystemService } from '../../../service/system.service';

@Component({
  selector: 'app-menu-create',
  templateUrl: './menu-create.component.html',
  styleUrls: ['./menu-create.component.css']
})
export class MenuCreateComponent implements OnInit {

  public baseUrl = environment.baseUrl;
  public myForm: FormGroup;
  public parentMenu: any = [];
  public isSubmitted = false;

  constructor(
    private formBuilder: FormBuilder,
    private datePipe: DatePipe,
    private route: ActivatedRoute,
    private router: Router,
    private spinnerService: NgxSpinnerService,
    private toastr:ToastrService,
    private systemService:SystemService
  ) {

  }

  ngOnInit(): void {
    this._initForm();
    this._getParentMenu();
  }

  _initForm(){

    this.myForm = this.formBuilder.group({
      id: [''],
      code: ['', [Validators.required,Validators.minLength(3)]],
      description: ['', [Validators.required]],
      parentMenu: [''],
      openUrl:['',[Validators.required]],
      iconHtml:[''],
      hasChild:[''],
      visibleToAll:[''],
      leftSideMenu:[''],
      isChild:[''],
      isActive:['',[Validators.required]],
      sequence:['',[Validators.required]],
    });

  }



  _getParentMenu(){
    const apiURL = this.baseUrl + '/system/systemMenu/getParentMenu';
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

    const apiURL = this.baseUrl + '/system/systemMenu/create';
    this.isSubmitted = true;
    if(this.myForm.invalid){
      return;
    }

    let formData: any;
    formData = Object.assign(this.myForm.value,{
      parentMenu: this._getParentMenuFormData().value ? {id: this._getParentMenuFormData().value} : null,
    });
    formData.rEntityName = 'SystemMenu';
    formData.rActiveOperation = 'Create';

    console.log(formData);

    this.spinnerService.show();
    this.systemService.sendPostRequest(apiURL, formData).subscribe(
      (response: any) => {
        if(response.status === true){
          console.log(response);
          this.spinnerService.hide().then(r => console.log('spinner stopped'));
          this.router.navigate(['/system/menu/list'], {relativeTo: this.route}).then(r => console.log('navigated'));
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

  }

  _getParentMenuFormData(){
    console.log(this.myForm.get('parentMenu').value);
    return this.myForm.get('parentMenu');
  }

}
