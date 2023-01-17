import { Component, OnInit } from '@angular/core';
import { Accounts } from 'src/app/models/accounts';
import { AccountService } from 'src/app/services/account/account.service';
import { AuthService } from 'src/app/services/auth/auth.service';

@Component({
  selector: 'app-profile-detail',
  templateUrl: './profile-detail.component.html',
  styleUrls: ['./profile-detail.component.css']
})
export class ProfileDetailComponent implements OnInit {

  account:Accounts;

  constructor(private authService:AuthService, private accountService:AccountService) { }

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
