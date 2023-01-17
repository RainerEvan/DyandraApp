import { Component, OnInit } from '@angular/core';
import { Accounts } from 'src/app/models/accounts';
import { AccountService } from 'src/app/services/account/account.service';
import { AccountAuthService } from 'src/app/services/auth/account-auth.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {

  account:Accounts;

  constructor(private authService:AccountAuthService, private accountService:AccountService) { }

  ngOnInit(): void {
    this.getAccount();
  }

  getAccount(){
    const userId = this.authService.accountValue.userId;

    this.accountService.getAccountByUserId(userId).subscribe({
      next:(response:Accounts)=>{
        this.account = response;
      },
      error:(error:any)=>{
          console.log(error);
      }
    })
  }
}
