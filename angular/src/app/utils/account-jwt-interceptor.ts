import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { environment } from "src/environments/environment";
import { AccountAuthService } from "../services/auth/account-auth.service";

@Injectable()
export class AccountJwtInterceptor implements HttpInterceptor {
    constructor(private accountAuthService: AccountAuthService) { }

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        const account = this.accountAuthService.accountValue;
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