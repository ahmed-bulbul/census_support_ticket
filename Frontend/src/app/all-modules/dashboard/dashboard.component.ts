import { Component, OnInit, HostListener, NgZone } from "@angular/core";
import { Router, Event, NavigationEnd } from "@angular/router";
import { LoginService } from "src/app/login/services/login.services";

@Component({
  selector: "app-dashboard",
  templateUrl: "./dashboard.component.html",
  styleUrls: ["./dashboard.component.css"],
})
@HostListener("window: resize", ["$event"])
export class DashboardComponent implements OnInit {
  public innerHeight: any;

  getScreenHeight() {
    this.innerHeight = window.innerHeight + "px";
  }

  constructor(private ngZone: NgZone, private router: Router,private loginService: LoginService) {

    window.onresize = (e) => {
      this.ngZone.run(() => {
        this.innerHeight = window.innerHeight + "px";
      });
    };
    this.getScreenHeight();
  }

  ngOnInit() {


    let authorities = this.loginService.getLoginUserRole();
    if(authorities.includes("ROLE_SUPER_ADMIN") || authorities.includes("ROLE_BBS_USER")){
      this.router.navigateByUrl("/dashboard/admin");
    }else{
      this.router.navigateByUrl("/dashboard/common");
    }
  }

  onResize(event) {
    this.innerHeight = event.target.innerHeight + "px";
  }
}
