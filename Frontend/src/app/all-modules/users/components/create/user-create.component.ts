import { ToastrService } from 'ngx-toastr';
import { UserService } from './../../service/user.service';
import { Component, OnInit } from '@angular/core';
import { AbstractControlOptions, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { environment } from 'src/environments/environment';
import { DatePipe } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { MustMatch } from 'src/app/Utils/must-match.validator';

@Component({
  selector: 'app-user-create',
  templateUrl: './user-create.component.html',
  styleUrls: ['./user-create.component.css']
})
export class UserCreateComponent implements OnInit {

  public baseUrl = environment.baseUrl;
  public myForm: FormGroup;
  public isSubmitted: boolean = false;
  public rolesData: any = [];
  public selectedRoles : any =[];
  public isGroupUser: boolean = true;
  public groupUserData :any = [];

  constructor(
    private formBuilder: FormBuilder,
    private datePipe: DatePipe,
    private userService: UserService,
    private route: ActivatedRoute,
    private router: Router,
    private spinnerService: NgxSpinnerService,
    private toastr:ToastrService
  ) { }

  ngOnInit(): void {

    this._initForm();
    this._getGroupUsers();
    this._getRoles();


  }

  _initForm(){

    const formOptions: AbstractControlOptions = { validators: MustMatch('password', 'confirmPassword') };
    this.myForm = this.formBuilder.group({
      id: [""],
      username: ["", [Validators.required,Validators.minLength(3)]],
      email: ["", [Validators.required,Validators.email]],
      phone: ["", [Validators.required,Validators.minLength(11), Validators.pattern("[0-9]+")]],
      groupUser: [""],
      userTitle:["", [Validators.required]],
      groupUsername: [""],
      password: ["", [Validators.required,Validators.minLength(4)]],
      confirmPassword: ['', Validators.required],
      role:[],
    }, formOptions);

  }

  checkRole( event,role){
    if(event.target.checked){
      this.selectedRoles.push(role);
    }else{
      let index = this.rolesData.indexOf(role);
      this.selectedRoles.splice(index,1);
    }

    console.log(this.selectedRoles);

  }

  checkGroupUser(event, value){
    if(value == "groupUser"){
      let isChecked =  $('#groupUserId').prop('checked');
      if(isChecked){
        this.isGroupUser = false;
      }else{
        this.isGroupUser = true;
      }
    }
  }

  _getGroupUsers() {
    let apiURL = this.baseUrl + "/user/getGroupUser";
    let queryParams: any = {};
    this.userService.sendGetRequest(apiURL,queryParams).subscribe((response: any) => {
      if(response.status == true){
        this.groupUserData = response.data;
      }else{
        this.toastr.error(response.message, 'Error');
      }
    });
  }

  myFormSubmit(){

    let apiURL = this.baseUrl + "/auth/register";

    this.isSubmitted = true;
    if(this.myForm.invalid){
      return;
    }

    let formData: any = {};
    formData = Object.assign(this.myForm.value,{
      role: this.selectedRoles,
    });
    formData.rActiveOperation = "Create";

    console.log(formData);

    this.spinnerService.show();
    this.userService.sendPostRequest(apiURL, formData).subscribe(
      (response: any) => {
        if(response.status == true){
          console.log(response);
          this.spinnerService.hide();
          this.router.navigate(["/users/user/list"], {relativeTo: this.route});
        }else{
          this.spinnerService.hide();
          this.toastr.error(response.message, 'Error');
        }

      },
      (error) => {
        console.log(error);
        this.spinnerService.hide();
      }
    );
  }

  _getRoles(){
    let apiURL = this.baseUrl + "/auth/roles";
    let queryParams: any = {};
    this.userService.sendGetRequest(apiURL,queryParams).subscribe((response: any) => {
      if(response.status == true){
        this.rolesData = response.data;
      }else{
        this.toastr.error(response.message, 'Error');
      }
    });
  }

  get f() { return this.myForm.controls; }

  resetFormValues(){
    this.myForm.reset();
  }

  getRoles(){
    console.log(this.myForm.get('roles').value);
    return this.myForm.get('roles');
  }

}


