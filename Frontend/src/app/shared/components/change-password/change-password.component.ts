import { SharedService } from './../../service/shared.service';
import { DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, AbstractControlOptions, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { ToastrService } from 'ngx-toastr';
import { LoginService } from 'src/app/login/services/login.services';
import { MustMatch } from 'src/app/Utils/must-match.validator';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.css']
})
export class ChangePasswordComponent implements OnInit {

  public baseUrl = environment.baseUrl;

  public changePassword: FormGroup;

  constructor(
    private formBuilder: FormBuilder,
    private datePipe: DatePipe,
    private route: ActivatedRoute,
    private router: Router,
    private spinnerService: NgxSpinnerService,
    private loginService:LoginService,
    private sharedService:SharedService,
    private toastr:ToastrService
  ) { }


  ngOnInit(): void {
    const formOptions: AbstractControlOptions = { validators: MustMatch('newPassword', 'confirmPassword') };
    this.changePassword = this.formBuilder.group({
      oldPassword: ['', [Validators.required]],
      newPassword: ['', [Validators.required]],
      confirmPassword: ['', [Validators.required]],
    },formOptions);
  }


  submitChangePassword() {
    if (this.changePassword.valid) {
    const apiURL = this.baseUrl + '/changePassword';
    let formData: any = {};
    formData = this.changePassword.value
    console.log(formData);
    this.spinnerService.show().then(r => {
      this.sharedService.sendPutRequest(apiURL,formData).subscribe((response: any) => {
        if(response.status === true){
          this.spinnerService.hide();
            this.toastr.success(response.message, 'Success');
            this.loginService.logout();
            this.router.navigate(['/login']);
        }else{
          this.spinnerService.hide();
          this.toastr.info(response.message, 'Info');
        }
      });
    });
    }else{
      this.spinnerService.hide().then(r => console.log(r));
      this.toastr.info('Please fill all required fields', 'Info');
    }
  }

}
