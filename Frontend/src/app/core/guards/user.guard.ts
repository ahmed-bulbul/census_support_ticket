import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { LoginService } from 'src/app/login/services/login.services';






@Injectable({
  providedIn: 'root'
})
export class UserGuard implements CanActivate {

  constructor(private login:LoginService,private router:Router){

  }
  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {

    //get current url
    console.log("current url : "+state.url);//'candidates'

    let authorities = this.login.getLoginUserRole();

    if(this.login.isLoggedIn() && ( authorities != null && (authorities.includes("ROLE_USER") || authorities.includes("ROLE_ADMIN") || authorities.includes("ROLE_SUPER_ADMIN") || authorities.includes("ROLE_BBS_USER")   || authorities.includes("ROLE_TIRE1_USER")  || authorities.includes("ROLE_TIRE2_USER"))) ){
        return true;
      }

    this.router.navigate(['login']);

    return false;
  }

}
