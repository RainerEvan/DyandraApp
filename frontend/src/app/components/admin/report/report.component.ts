import { Component, OnInit } from '@angular/core';
import { cloneDeep } from '@apollo/client/utilities';
import { DialogService, DynamicDialogRef } from 'primeng/dynamicdialog';
import { Reports } from 'src/app/models/reports';
import { ReportService } from 'src/app/services/report/report.service';
import { AddReportComponent } from '../../dialog/admin/add-report/add-report.component';
import { ConfirmationDialogComponent } from '../../dialog/shared/confirmation-dialog/confirmation-dialog.component';

@Component({
  selector: 'app-report',
  templateUrl: './report.component.html',
  styleUrls: ['./report.component.scss']
})
export class ReportComponent implements OnInit {

  reports:Reports[];
  loading:boolean;

  ref: DynamicDialogRef;

  constructor(private reportService:ReportService, private dialogService:DialogService) { }

  ngOnInit(): void {
    this.getAllReports();
  }

  getAllReports(){
    this.loading = true;

    this.reportService.getAllReports().subscribe({
      next:(response:Reports[])=>{
          this.reports = cloneDeep(response);
          this.loading = false;
      },
      error:(error:any)=>{
          console.log(error);
      }
    })
  }

  deleteReport(reportId:string){
    
  }

  showAddReportDialog(){
    this.ref = this.dialogService.open(AddReportComponent,{
        header: 'Add Report',
        baseZIndex: 10000,
        contentStyle: {"max-height": "650px", "width":"40vw", "min-width":"350px", "max-width":"500px","overflow": "auto"},
    });

    this.ref.onClose.subscribe((success:boolean)=>{
        if(success){
          this.getAllReports();
        }
    });
  }

  showConfirmationDialog(title:string, message:string, action:string, reportId:string){
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
            this.deleteReport(reportId);
          }
        }
    });
  }
}
