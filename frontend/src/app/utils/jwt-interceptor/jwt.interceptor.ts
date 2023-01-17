import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from 'src/app/services/auth/auth.service';
import { environment } from 'src/environments/environment';

@Injectable()
export class JwtInterceptor implements HttpInterceptor {

  constructor(private authService: AuthService) { }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
      const account = this.authService.accountValue;
      const isLoggedIn = account && account.accessToken;
      const isApiUrl = request.url.startsWith(environment.apiUrl);

      if (isLoggedIn && isApiUrl) {
          request = request.clone({
              setHeaders: {
                  Authorization: `Bearer ${account.accessToken}`
              }
          });
      }

      return next.handle(request);
  }
}
