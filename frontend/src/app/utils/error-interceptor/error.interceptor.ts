import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http';
import { Observable, catchError, throwError } from 'rxjs';
import { AuthService } from 'src/app/services/auth/auth.service';

@Injectable()
export class ErrorInterceptor implements HttpInterceptor {

  constructor(private authService: AuthService) { }
  
  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(request).pipe(catchError(err => {
        if ([401, 403].indexOf(err.status) !== -1) {
            this.authService.logout();
        }

        const error = err.error.message || err.statusText;
        return throwError(() => new Error(error));
    }))
  }
}
