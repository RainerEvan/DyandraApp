import { Component, OnInit } from '@angular/core';
import { cloneDeep } from '@apollo/client/utilities';
import { DialogService, DynamicDialogRef } from 'primeng/dynamicdialog';
import { Applications } from 'src/app/models/applications';
import { ApplicationService } from 'src/app/services/application/application.service';
import { AddApplicationComponent } from '../../dialog/admin/add-application/add-application.component';
import { ConfirmationDialogComponent } from '../../dialog/shared/confirmation-dialog/confirmation-dialog.component';

@Component({
  selector: 'app-application',
  templateUrl: './application.component.html',
  styleUrls: ['./application.component.scss']
})
export class ApplicationComponent implements OnInit {

  applications:Applications[];
  loading:boolean;

  ref: DynamicDialogRef;

  constructor(private applicationService:ApplicationService, private dialogService:DialogService) { }

  ngOnInit(): void {
    this.getAllApplications();
  }

  getAllApplications(){
    this.loading = true;

    this.applicationService.getAllApplications().subscribe({
      next:(response:Applications[])=>{
          this.applications = cloneDeep(response);
          this.loading = false;
      },
      error:(error:any)=>{
          console.log(error);
      }
    })
  }

  deleteApplication(applicationId:string){

  }

  showAddApplicationDialog(){
    this.ref = this.dialogService.open(AddApplicationComponent,{
        header: 'Add Application',
        baseZIndex: 10000,
        contentStyle: {"max-height": "650px", "width":"40vw", "min-width":"350px", "max-width":"500px","overflow": "auto"},
    });

    this.ref.onClose.subscribe((success:boolean)=>{
        if(success){
          this.getAllApplications();
        }
    });
  }

  showConfirmationDialog(title:string, message:string, action:string, applicationId:string){
    this.ref = this.dialogService.open(ConfirmationDialogComponent, {
      header: title,
      data: {
        message: message,
      },
      baseZIndex: 10000,
      contentStyle: {"max-height": "500px", "width":"40vw", "min-width":"350px", "max-width":"500px", "overflow": "auto"},
    });

    this.ref.onClose.subscribe((confirm:boolean) =>{
        if (confirm) {
          if(action == 'delete'){
            this.deleteApplication(applicationId);
          }
        }
    });
  }
}
