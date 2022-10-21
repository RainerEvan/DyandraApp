import { Component, OnInit } from '@angular/core';
import { DialogService, DynamicDialogRef } from 'primeng/dynamicdialog';
import { Reports } from 'src/app/models/reports';
import { ReportService } from 'src/app/services/report/report.service';
import { AddReportComponent } from '../../dialog/admin/add-report/add-report.component';

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
          this.reports = response;
          this.loading = false;
      },
      error:(error:any)=>{
          console.log(error);
      }
    })
  }

  showAddReportDialog(){
    this.ref = this.dialogService.open(AddReportComponent,{
        header: 'Add Report',
        baseZIndex: 10000,
        contentStyle: {"max-height": "650px", "min-width":"30vw","overflow": "auto"},
    });

    this.ref.onClose.subscribe((success:boolean)=>{
        if(success){
          this.getAllReports();
        }
    });
  }

}
