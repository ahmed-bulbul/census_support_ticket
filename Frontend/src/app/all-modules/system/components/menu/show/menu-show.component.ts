import { DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { ToastrService } from 'ngx-toastr';
import { environment } from 'src/environments/environment';
import { SystemService } from '../../../service/system.service';


@Component({
  selector: 'app-menu-show',
  templateUrl: './menu-show.component.html',
  styleUrls: ['./menu-show.component.css']
})
export class MenuShowComponent implements OnInit {

  public baseUrl = environment.baseUrl;
  public myForm: FormGroup;
  public myFormData: any = {};

  constructor(
    private formBuilder: FormBuilder,
    private datePipe: DatePipe,
    private route: ActivatedRoute,
    private router: Router,
    private spinnerService: NgxSpinnerService,
    private systemService: SystemService,
    private toastr : ToastrService,
  ) { }

  ngOnInit(): void {
    this._getFormData();
  }

  _getFormData(){

      const id =  this.route.snapshot.params.id;
      const apiURL = this.baseUrl + '/system/systemMenu/get/' + id;

      const queryParams: any = {};
      queryParams.rEntityName = 'SystemMenu';
      queryParams.rActiveOperation = 'read';

      this.spinnerService.show();
      this.systemService.sendGetRequest(apiURL, queryParams)
        .subscribe(
          response => {
            if(response.status === true){
              this.spinnerService.hide();
              this.myFormData = response.data;
            }else{
              this.spinnerService.hide();
              this.toastr.error(response.message, 'Error');
            }
          },
          error => {
            this.spinnerService.hide();
            console.log('Error: ', error);
          }
        );
  }

}
