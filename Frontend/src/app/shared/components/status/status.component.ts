import { DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { ToastrService } from 'ngx-toastr';
import { LoginService } from 'src/app/login/services/login.services';
import { environment } from 'src/environments/environment';
import { SharedService } from '../../service/shared.service';

@Component({
  selector: 'app-status',
  templateUrl: './status.component.html',
  styleUrls: ['./status.component.css']
})
export class StatusComponent implements OnInit {


  public baseUrl = environment.baseUrl;

  public pipe = new DatePipe('en-US');
  public myFromGroup: FormGroup;
  public listData: any = [];


  // search fields for
  private code: string;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private spinnerService: NgxSpinnerService,
    private toastr:ToastrService,
    private sharedService:SharedService,
    private loginService:LoginService
  ) { }

  ngOnInit(): void {
    this.code = this.route.snapshot.queryParamMap.get('code');
    if(this.code != null){
      this._getListData();
      //set interval 1 sec
      setInterval(() => {
        this.refreshTime();
      }
        , 1000);
    }

  }


  refreshTime() {
    var dateString = new Date().toLocaleString("en-US", {timeZone: "Asia/Dhaka"});
    var formattedString = dateString.replace(", ", " - ");
    //document.getElementById("time").innerHTML = formattedString;
  }





  _getListData(){
    const apiURL = this.baseUrl + '/ticket/bbs/getStatus';

    let queryParams: any = {};
    const params = this.getUserQueryParams();
    queryParams = params;

    queryParams.rEntityName = 'TicketLog';
    queryParams.rReqType = 'getListData';


    this.spinnerService.show();
    if(params[`code`] != null){
      this.sharedService.sendGetRequest(apiURL, queryParams).subscribe(
        (response: any) => {
          if(response.status === true){
            this.listData = response.data;
            this.spinnerService.hide();
          }else{
            this.spinnerService.hide();
            this.toastr.info(response.message, 'Info');
          }

        },
        (error) => {
          console.log(error)
        }
      );
    }


  }
  private getUserQueryParams(): any {

    const params: any = {};

    // push other attributes
    if(this.code){
      params[`code`] = this.code;
    }
    // if(this.creationUser){
    //   params['creationUser']= this.creationUser;
    // }


    return params;

  }

  searchByCode(code: string){
    this.code = code;
    this._getListData();

  }

}
