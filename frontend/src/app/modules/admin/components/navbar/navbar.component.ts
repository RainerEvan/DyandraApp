import { Component, OnInit } from '@angular/core';
import { DialogService, DynamicDialogRef } from 'primeng/dynamicdialog';
import { ConfirmationDialogComponent } from 'src/app/modules/shared/components/confirmation-dialog/confirmation-dialog.component';
import { AuthService } from 'src/app/services/auth/auth.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  showDropdown:boolean = false;
  ref: DynamicDialogRef;

  constructor(public dialogService:DialogService, private authService:AuthService) { }

  ngOnInit(): void {
  }

  ngOnDestroy(): void {
    if(this.ref){
      this.ref.close();
    }
  }

  toggleDropdown(){
    this.showDropdown = !this.showDropdown;
  }

  showConfirmationDialog(title:string, message:string, action:string){
    this.ref = this.dialogService.open(ConfirmationDialogComponent, {
      header: title,
      data: {
        message: message,
      },
      baseZIndex: 10000,
      contentStyle: {"max-height": "650px","width":"40vw", "min-width":"350px", "max-width":"500px", "overflow": "auto"},
    });

    this.ref.onClose.subscribe((confirm:boolean) =>{
        if (confirm) {
          if(action == 'logout'){
            this.authService.logout();
          }
        }
    });
  }
}
