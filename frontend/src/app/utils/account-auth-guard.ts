import { Injectable } from "@angular/core";
import { CanActivate, Router } from "@angular/router";
import { AccountAuthService } from "../services/auth/account-auth.service";

@Injectable({
    providedIn: 'root'
  })
  export class AccountAuthGuard implements CanActivate {
  
    constructor(private router: Router, private accountAuthService: AccountAuthService) { }
  
    canActivate(): boolean {
      const account = this.accountAuthService.accountValue;
  
      if(account){
        return true;
      }
  
      this.router.navigate(['/admin/login']);
      return false;
    }
  }
  