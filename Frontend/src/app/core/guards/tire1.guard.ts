import { ToastrService } from 'ngx-toastr';
import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { LoginService } from 'src/app/login/services/login.services';






@Injectable({
  providedIn: 'root'
})
export class Tire1Guard implements CanActivate {

  constructor(private login:LoginService,private router:Router,private toastr:ToastrService){

  }
  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {

    //get current url
    console.log("current url : "+state.url);//'candidates'

    let authorities = this.login.getLoginUserRole();

    if(this.login.isLoggedIn() && (authorities != null && (authorities.includes("ROLE_TIRE1_USER")) ||(authorities.includes("ROLE_SUPER_ADMIN")) )){

        return true;
    }
    this.toastr.info("You are not authorized to access this page");
    this.router.navigate(['/dashboard/admin']);
    return false;
  }

}
