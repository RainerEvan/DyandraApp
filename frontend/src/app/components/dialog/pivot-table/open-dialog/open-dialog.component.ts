import { Component, OnInit } from '@angular/core';
import { cloneDeep } from '@apollo/client/utilities';
import { DynamicDialogRef } from 'primeng/dynamicdialog';
import { Reports } from 'src/app/models/reports';
import { ReportService } from 'src/app/services/report/report.service';

@Component({
  selector: 'app-open-dialog',
  templateUrl: './open-dialog.component.html',
  styleUrls: ['./open-dialog.component.scss']
})
export class OpenDialogComponent implements OnInit {

  reports:Reports[];
  loading:boolean;
  selectedReport:Reports;

  constructor(public ref: DynamicDialogRef, private reportService: ReportService) { }

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

  openReport(){
    if(this.selectedReport){
      this.ref.close(this.selectedReport.reportId);
    }
  }
}
