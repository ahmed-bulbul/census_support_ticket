import { User } from './../../users/model/User';
import { Component, OnInit } from "@angular/core";
import { LoginService } from "src/app/login/services/login.services";
import { environment } from "src/environments/environment";
import { DashboardService } from "../service/dashboard.service";
import { interval } from 'rxjs';

@Component({
  selector: "app-admin-dashboard",
  templateUrl: "./admin-dashboard.component.html",
  styleUrls: ["./admin-dashboard.component.css"],
})
export class AdminDashboardComponent implements OnInit {

  public baseUrl = environment.baseUrl;
  private polling: any;
  public user:any;
  public totalTickets = 0;
  public totalReceivedTickets = 0;
  public totalHoldTickets = 0;
  public totalResolvedTickets = 0;


  constructor(
    private loginService:LoginService,
    private dashboardService:DashboardService
  ) { }

  ngOnInit() {
    this.user = this.loginService.getUser();
    this.pollData();

  }

  pollData () {
    this.polling = setInterval(() => {
      this.getTotalTicket();
      this.getTotalReceivedTicket();
      this.getTotalHoldTicket();
      this.getTotalResolvedTicket();

  },2000);
}

  // getTotalTicket
  getTotalTicket() {
    const apiURL = this.baseUrl + '/dashboard/getTotalTickets';
    let queryParams: any = {};

    this.dashboardService.sendGetRequest(apiURL, queryParams).subscribe(
      (response: any) => {
        this.totalTickets = response;
      },
      (error) => {
        console.log(error)
      }
    );
  }

  // getTotalReceivedTicket
  getTotalReceivedTicket() {
    const apiURL = this.baseUrl + '/dashboard/getReceivedTickets';
    let queryParams: any = {};

    this.dashboardService.sendGetRequest(apiURL, queryParams).subscribe(
      (response: any) => {
        this.totalReceivedTickets = response;
      },
      (error) => {
        console.log(error)
      }
    );
  }

  // getTotalHoldTicket
  getTotalHoldTicket() {
    const apiURL = this.baseUrl + '/dashboard/getHoldTickets';
    let queryParams: any = {};

    this.dashboardService.sendGetRequest(apiURL, queryParams).subscribe(
      (response: any) => {
        this.totalHoldTickets = response;
      },
      (error) => {
        console.log(error)
      }
    );
  }

  //get total resolved ticket
  getTotalResolvedTicket() {
    const apiURL = this.baseUrl + '/dashboard/getResolvedTickets';
    let queryParams: any = {};

    this.dashboardService.sendGetRequest(apiURL, queryParams).subscribe(
      (response: any) => {
        this.totalResolvedTickets = response;
      },
      (error) => {
        console.log(error)
      }
    );
  }

  ngOnDestroy() {
    clearInterval(this.polling);
}
}
