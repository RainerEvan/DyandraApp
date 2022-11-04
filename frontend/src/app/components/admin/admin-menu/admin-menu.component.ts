import { Component, OnInit } from '@angular/core';
import { AccountAuthService } from 'src/app/services/auth/account-auth.service';

@Component({
  selector: 'app-admin-menu',
  templateUrl: './admin-menu.component.html',
  styleUrls: ['./admin-menu.component.scss']
})
export class AdminMenuComponent implements OnInit {

  showDropdown:boolean = false;

  constructor(private accountAuthService:AccountAuthService) { }

  ngOnInit(): void {
  }

  toggleDropdown(){
    this.showDropdown = !this.showDropdown;
  }

  logout(){
    this.accountAuthService.logout();
  }

}
