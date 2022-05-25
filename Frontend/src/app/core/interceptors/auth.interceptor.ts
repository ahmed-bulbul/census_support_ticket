import { HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest, HTTP_INTERCEPTORS } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Router } from "@angular/router";
import { ToastrService } from "ngx-toastr";
import { Observable, of, throwError } from "rxjs";
import { catchError } from "rxjs/operators";
import { LoginService } from "src/app/login/services/login.services";







@Injectable()
export class AuthInterceptor implements HttpInterceptor{

    constructor(private loginService:LoginService,private router:Router,  private toastr: ToastrService,){

    }

    private handleAuthError(err: HttpErrorResponse): Observable<any> {
        //handle your auth error or rethrow
        if (err.status === 403) {
            //navigate /delete cookies or whatever
            this.toastr.error('you are not athorized or token has been expired','error');
            this.router.navigate(['error/error403']);
            return of(err.message); // or EMPTY may be appropriate here
        }else if(err.status === 500){
            console.log(err.message);
            if(err.message.includes('jwt token has expired')){
                this.loginService.logout();
                this.router.navigate(['login']);
            }
           // this.router.navigate(['error/error500']);
        }else if(err.status === 401){
            console.log("From 401");
            this.loginService.logout();
            this.router.navigate(['login']);
            window.location.reload();
        }else if(err.status === 404){
            this.router.navigate(['error/error404']);
        }
        //server down
        else if(err.status === 0){
          this.toastr.info('Server is not responding. Please try again later.','info',{
              timeOut:5000
          })
          this.router.navigate(['error/error500']);

        }
        return throwError(err);
    }

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

        //add the jwt token (LocalStorage) request
        let authReq=req;
        const token=this.loginService.getToken();


        if(token !=null){
            authReq=authReq.clone({
                setHeaders:{Authorization:`Bearer ${token}`},
            });

        }


        return next.handle(authReq).pipe(catchError(x=> this.handleAuthError(x)));
    }

}

export const authInterceptorProviders=[
    {
        provide: HTTP_INTERCEPTORS,
        useClass: AuthInterceptor,
        multi: true
    }
];
