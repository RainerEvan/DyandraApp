import { DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { DynamicDialogConfig, DynamicDialogRef } from 'primeng/dynamicdialog';
import { Reports } from 'src/app/models/reports';
import { ReportService } from 'src/app/services/report/report.service';

@Component({
  selector: 'app-report-details',
  templateUrl: './report-details.component.html',
  styleUrls: ['./report-details.component.scss']
})
export class ReportDetailsComponent implements OnInit {

  reportForm: FormGroup;
  isReportFormSubmitted: boolean = false;
  report: Reports;
  
  constructor(public ref: DynamicDialogRef, public config: DynamicDialogConfig, private datePipe:DatePipe, private reportService:ReportService, private formBuilder: FormBuilder) { }

  ngOnInit(): void {
    this.report = this.config.data.reportData;
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
          console.log(result);
        },
        error: (error: any) => {
          this.isReportFormSubmitted = false;
          this.ref.close(this.isReportFormSubmitted);
          console.log(error);
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

  get title(){
    return this.reportForm.get('title');
  }
  get reportId(){
    return this.reportForm.get('reportId');
  }
  get connection(){
    return this.reportForm.get('connection');
  }
  get sourcePath(){
    return this.reportForm.get('sourcePath');
  }
  get query(){
    return this.reportForm.get('query');
  }
  get dateCreated(){
    return this.reportForm.get('dateCreated');
  }

}
