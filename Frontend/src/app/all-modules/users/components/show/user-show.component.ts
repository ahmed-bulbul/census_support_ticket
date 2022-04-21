import { UserService } from './../../service/user.service';
import { DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { environment } from 'src/environments/environment';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-user-show',
  templateUrl: './user-show.component.html',
  styleUrls: ['./user-show.component.css']
})
export class UserShowComponent implements OnInit {

  public baseUrl = environment.baseUrl;
  public myForm: FormGroup;
  public myFormData: any = {};

  constructor(
    private formBuilder: FormBuilder,
    private datePipe: DatePipe,
    private route: ActivatedRoute,
    private router: Router,
    private spinnerService: NgxSpinnerService,
    private UserService: UserService,
    private toastr : ToastrService,
  ) { }

  ngOnInit(): void {
    this._getFormData();
  }

  _getFormData(){

    let id =  this.route.snapshot.params.id;
    let apiURL = this.baseUrl + "/acl/user/get/" + id;

    let queryParams: any = {};
    queryParams.rEntityName = "User";
    queryParams.rActiveOpetation = "read";

    this.spinnerService.show();
    this.UserService.sendGetRequest(apiURL, queryParams).subscribe(
      (response: any) => {
        if(response.status == true){
          this.myFormData = response.data;
          this.spinnerService.hide();
          this.myForm.patchValue(this.myFormData);
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

  saveUpdatedFormData(){

  }

}
