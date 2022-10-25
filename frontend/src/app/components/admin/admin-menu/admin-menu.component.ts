import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-admin-menu',
  templateUrl: './admin-menu.component.html',
  styleUrls: ['./admin-menu.component.scss']
})
export class AdminMenuComponent implements OnInit {

  showDropdown:boolean = false;

  constructor() { }

  ngOnInit(): void {
  }

  toggleDropdown(){
    this.showDropdown = !this.showDropdown;
  }

}
