import { Component, OnInit } from '@angular/core';
import { cloneDeep } from '@apollo/client/utilities';
import { DialogService, DynamicDialogRef } from 'primeng/dynamicdialog';
import { Applications } from 'src/app/models/applications';
import { ApplicationService } from 'src/app/services/application/application.service';
import { AddApplicationComponent } from '../add-application/add-application.component';
import { ApplicationDetailComponent } from '../application-detail/application-detail.component';
import { ConfirmationDialogComponent } from 'src/app/modules/shared/components/confirmation-dialog/confirmation-dialog.component';

@Component({
  selector: 'app-application-list',
  templateUrl: './application-list.component.html',
  styleUrls: ['./application-list.component.css']
})
export class ApplicationListComponent implements OnInit {

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
    this.applicationService.deleteApplication(applicationId).subscribe({
      next: (result: any) => {
        console.log(result);
        this.getAllApplications();
      },
      error: (error: any) => {
        console.log(error);
      }
    });
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

  showApplicationDetailsDialog(application:Applications){
    this.ref = this.dialogService.open(ApplicationDetailComponent,{
        header: 'Application Details',
        data:{
          applicationData:application
        },
        footer:" ",
        baseZIndex: 10000,
        contentStyle: {"max-height": "650px", "width":"40vw", "min-width":"350px", "max-width":"500px","overflow": "auto"},
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
