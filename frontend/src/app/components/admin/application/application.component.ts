import { Component, OnInit } from '@angular/core';
import { DialogService, DynamicDialogRef } from 'primeng/dynamicdialog';
import { Applications } from 'src/app/models/applications';
import { ApplicationService } from 'src/app/services/application/application.service';
import { AddApplicationComponent } from '../../dialog/admin/add-application/add-application.component';

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
          this.applications = response;
          this.loading = false;
      },
      error:(error:any)=>{
          console.log(error);
      }
    })
  }

  showAddApplicationDialog(){
    this.ref = this.dialogService.open(AddApplicationComponent,{
        header: 'Add Application',
        baseZIndex: 10000,
        contentStyle: {"max-height": "650px", "min-width":"30vw","overflow": "auto"},
    });

    this.ref.onClose.subscribe((success:boolean)=>{
        if(success){
          this.getAllApplications();
        }
    });
  }

}
