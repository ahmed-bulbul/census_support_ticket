import { DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { LoginService } from 'src/app/login/services/login.services';
import { environment } from 'src/environments/environment';

declare const $: any;
@Component({
  selector: 'app-common-dashboard',
  templateUrl: './common-dashboard.component.html',
  styleUrls: ['./common-dashboard.component.css'],
  providers: [DatePipe]
})


export class CommonDashboardComponent implements OnInit {

  public baseUrl = environment.baseUrl;
  public user:any;

  myDate = Date.now();    //date
  constructor(  private loginService:LoginService,private datePipe: DatePipe) {
   // this.myDate = this.datePipe.transform(this.myDate, 'yyyy-MM-dd');
  }

  ngOnInit(): void {
    this.user = this.loginService.getUser();
  }

}
