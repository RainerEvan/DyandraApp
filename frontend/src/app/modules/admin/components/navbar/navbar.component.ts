import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/services/auth/auth.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  showDropdown:boolean = false;

  constructor(private authService:AuthService) { }

  ngOnInit(): void {
  }

  toggleDropdown(){
    this.showDropdown = !this.showDropdown;
  }

  logout(){
    this.authService.logout();
  }
}
