import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { ToastrService } from 'ngx-toastr';
import { delay } from 'rxjs/operators';
import { environment } from 'src/environments/environment';
import { Login } from '../model/login';
import { LoginService } from '../services/login.services';



@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  public myForm: FormGroup;
  public baseUrl = environment.baseUrl;
  public formSubmitted = false;

  constructor(
    private formBuilder: FormBuilder,
    private toastr: ToastrService,
    private loginService: LoginService,
    private router: Router,
    private spinnerService:NgxSpinnerService
  ) { }

  ngOnInit() {
    this._initForm();
    this._checkIsLoogedIn();

  }

  // Initialize form function
  _initForm(){
    this.myForm=this.formBuilder.group({
      username:["",Validators.required],
      password: ['', [Validators.minLength(4),  Validators.required]],
    });
  }

  _checkIsLoogedIn(){
    if(this.loginService.isLoggedIn()){
      this.router.navigate(['dashboard']);
    }
  }

  // Get form control
  get f() { return this.myForm.controls; }

  // Form submit for login request
  async onSubmit(){

    let apiURL = this.baseUrl + "/acl/login";

    this.formSubmitted = true;

    //check form validation; if error return error
    if(this.myForm.invalid) return;

    //fetch login form data
    let formData :Login=Object.assign(this.myForm.value);

    //call service
    console.log("@Send post request for login");
    this.spinnerService.show();
    this.loginService.login(apiURL,formData).pipe(delay(1300)).subscribe((data:any) =>{
      if(data.status=== true){
        console.log(data);
        this.loginService.setLocalStorage(data);
        let authorities = this.loginService.getLoginUserRole();

        if(authorities.includes("ROLE_BBS_USER")){
          this.toastr.success('You are now authenticated','Success', { positionClass:'toast-custom' })
          this.spinnerService.hide();
          this.router.navigate(['/ticket/bbs/list']);
          this.loginService.loginStatusSubject.next(true);

        }else if(authorities.includes("ROLE_TIRE1_USER")){
          this.toastr.success('You are now authenticated','Success', { positionClass:'toast-custom' })
          this.spinnerService.hide();
          this.router.navigate(['/dashboard/admin2']);
          this.loginService.loginStatusSubject.next(true);
        }

        else if(authorities.includes("ROLE_TIRE2_USER")){
          this.toastr.success('You are now authenticated','Success', { positionClass:'toast-custom' })
          this.spinnerService.hide();
          this.router.navigate(['/dashboard/admin3']);
          this.loginService.loginStatusSubject.next(true);
        }


        else if(authorities.includes("ROLE_SUPER_ADMIN")){
          this.toastr.success('You are now authenticated','Success', { positionClass:'toast-custom' })
          this.spinnerService.hide();
          this.router.navigate(['/dashboard/admin']);
          this.loginService.loginStatusSubject.next(true);
        }

        else{
          this.spinnerService.hide();
          this.toastr.error('Something went wrong','error', { positionClass:'toast-custom' });
          this.loginService.logout();
        }
      }else{
        this.spinnerService.hide();
        this.toastr.info(data.message,'error', { positionClass:'toast-custom' });
      }


    },(error) =>{this.toastr.error(''+error.error.code)});

  }



}
