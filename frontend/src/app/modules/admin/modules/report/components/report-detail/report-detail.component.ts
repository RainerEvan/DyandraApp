import { DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { DialogService, DynamicDialogConfig, DynamicDialogRef } from 'primeng/dynamicdialog';
import { Reports } from 'src/app/models/reports';
import { ResultDialogComponent } from 'src/app/modules/shared/components/result-dialog/result-dialog.component';
import { ReportService } from 'src/app/services/report/report.service';

@Component({
  selector: 'app-report-detail',
  templateUrl: './report-detail.component.html',
  styleUrls: ['./report-detail.component.css']
})
export class ReportDetailComponent implements OnInit {

  reportForm: FormGroup;
  isReportFormSubmitted: boolean = false;
  report: Reports;
  methodName: String;
  
  constructor(public ref: DynamicDialogRef, public config: DynamicDialogConfig, private dialogService:DialogService, private datePipe:DatePipe, private reportService:ReportService, private formBuilder: FormBuilder) { }

  ngOnInit(): void {
    this.report = this.config.data.reportData;
    this.methodName = this.report.connection.method.name;
    this.generateReportForm();
  }

  public editReport(): void{
    if(this.reportForm.valid){
      const reportId = this.report.id;
      const report = this.reportForm.value;

      this.reportService.editReport(reportId,report).subscribe({
        next: (result: any) => {
          this.isReportFormSubmitted = true;
          this.ref.close(this.isReportFormSubmitted);
          this.showResultDialog("Success","Report has been updated successfully");
        },
        error: (error: any) => {
          this.isReportFormSubmitted = false;
          this.ref.close(this.isReportFormSubmitted);
          this.showResultDialog("Failed","There was a problem, try again later");
        }
      });
    } 
  }

  generateReportForm(){
    this.reportForm = this.formBuilder.group({
      title: [this.report.title],
      reportId: [{value:this.report.reportId, disabled:true}],
      connection: [{value:this.report.connection.name, disabled:true}],
      sourcePath: [{value:this.report.sourcePath.path, disabled:true}],
      dateCreated:[{value:this.datePipe.transform(this.report.createdAt,'dd/MM/yyyy'), disabled:true}],
      query: [this.report.query],
    });
  }

  showResultDialog(title:string, message:string){
    this.ref = this.dialogService.open(ResultDialogComponent, {
      header: title,
      data: {
        message: message,
      },
      baseZIndex: 10000,
      contentStyle: {"max-height": "650px","width":"40vw", "min-width":"350px", "max-width":"500px", "overflow": "auto"},
    });
  }
}
