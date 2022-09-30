import { Component, OnInit } from '@angular/core';
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

  constructor(private reportService: ReportService) { }

  ngOnInit(): void {
    this.getAllReports();
  }

  getAllReports(){
    this.loading = true;

    this.reportService.getAllAccounts().subscribe({
      next:(response:Reports[])=>{
          this.reports = response;
          this.loading = false;
      },
      error:(error:any)=>{
          console.log(error);
      }
    })
  }

}
