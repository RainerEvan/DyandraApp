import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { catchError, Observable, throwError } from "rxjs";
import { AccountAuthService } from "../services/auth/account-auth.service";

@Injectable({
    providedIn: 'root'
  })
  export class ErrorInterceptor implements HttpInterceptor{
  
    constructor(private accountAuthService: AccountAuthService) { }
  
    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
      return next.handle(request).pipe(catchError(err => {
          if ([401, 403].indexOf(err.status) !== -1) {
              this.accountAuthService.logout();
          }
  
          const error = err.error.message || err.statusText;
          return throwError(() => new Error(error));
      }))
    }
  }
  