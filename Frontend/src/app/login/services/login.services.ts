import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Router } from "@angular/router";
import { ToastrService } from "ngx-toastr";
import { Observable, Subject } from "rxjs";
import { retry } from "rxjs/operators";
import { environment } from "src/environments/environment";


@Injectable({
  providedIn: "root",
})
export class LoginService {
  baseUrl = environment.baseUrl;



  public loginStatusSubject = new Subject<boolean>();
  clearTimeout: any;

  constructor(private http: HttpClient, private toastr: ToastrService,private router: Router,) {}

  // user login
  public login(apiUrl,formData){
    return this.http.post(apiUrl, formData);
  }
  //set user data on  localstorage
  public setLocalStorage(data){
    //set token
    localStorage.setItem("token", data.accessToken);
    //set user
    localStorage.setItem("user", JSON.stringify(data.user));

  }

  //isLoogedIn: user is loggedin or not
  public isLoggedIn() {
    let tokenStr = localStorage.getItem("token");
    if (tokenStr == undefined || tokenStr == "" || tokenStr == null) {
      return false;
    } else {
      return true;
    }
  }


  //logout:: remove token from localstorage
  public logout() {
    localStorage.removeItem("token");
    localStorage.removeItem("user");
    if (this.clearTimeout) {
      clearTimeout(this.clearTimeout);
    }
    return true;
  }

  //getToken
  public getToken() {
    return localStorage.getItem("token");
  }


  //getUser
  public getUser() {
    let userStr = localStorage.getItem("user");
    if (userStr != null) {
      console.log("Current user "+JSON.parse(userStr))
      return JSON.parse(userStr);
    } else {
      this.logout();
      return null;
    }
  }


  //get user role
  public getLoginUserRole() {
    let userAuthorities = "";

    let loginUser = this.getUser();
    if (loginUser) {
      let authorities = loginUser.roles;
      authorities.forEach((element) => {
        userAuthorities = userAuthorities + element.authority + ",";
      });
    }
    console.log("userAuthorities [] " + userAuthorities);
    return userAuthorities;
  }

  // Register User
  public register(user) {
    return this.http.post(`${this.baseUrl}/user/register`, user);
  }


  //default
  public sendPostRequest(apiURL, formData){
    console.log('@sendPostRequest');
    return this.http.post(apiURL, formData);
  }


  public sendGetRequest(apiURL, queryParams){
    console.log('@sendGetRequest');
    return this.http.get<any>(apiURL, {params: queryParams}).pipe( retry(3));
  }

  public sendPutRequest(apiURL, formData){
    console.log('@sendPutRequest');
    return this.http.put(apiURL, formData);
  }


}
