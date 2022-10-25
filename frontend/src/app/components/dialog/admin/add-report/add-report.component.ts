import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { DynamicDialogRef } from 'primeng/dynamicdialog';
import { Connections } from 'src/app/models/connections';
import { SourcePaths } from 'src/app/models/sourcepaths';
import { ConnectionService } from 'src/app/services/connection/connection.service';
import { ReportService } from 'src/app/services/report/report.service';
import { SourcepathService } from 'src/app/services/sourcepath/sourcepath.service';

@Component({
  selector: 'app-add-report',
  templateUrl: './add-report.component.html',
  styleUrls: ['./add-report.component.scss']
})
export class AddReportComponent implements OnInit {

  reportForm: FormGroup;
  isReportFormSubmitted: boolean = false;
  connections: Connections[];
  sourcePaths: SourcePaths[];
  
  constructor(public ref: DynamicDialogRef, private reportService:ReportService, private sourcePathService: SourcepathService, private connectionService:ConnectionService, private formBuilder: FormBuilder) { }

  ngOnInit(): void {
    this.generateReportForm();
  }

  public getAllconnections(): void{
    this.connectionService.getAllConnections().subscribe({
      next:(response:Connections[])=>{
        this.connections = response;
      },
      error:(error:any)=>{
        console.log(error);
      }
    });
  }

  public getAllSourcePathsForConnection(event:any){
    this.reportForm.controls['sourcePathId'].enable();

    this.sourcePathService.getAllSourcePathsForConnection(event.value).subscribe({
      next:(response:SourcePaths[])=>{
        this.sourcePaths = response;
      },
      error:(error:any)=>{
        console.log(error);
      }
    });
  }

  public addReport(): void{
    if(this.reportForm.valid){
      const report = this.reportForm.value;

      this.reportService.addReport(report).subscribe({
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
      connectionId: [null, [Validators.required]],
      sourcePathId: [{value:null, disabled:true}, [Validators.required]],
      query: [null],
      title: [null, [Validators.required]],
    });
    this.getAllconnections();
  }

  get connectionId(){
    return this.reportForm.get('connectionId');
  }
  get sourcePathId(){
    return this.reportForm.get('sourcePathId');
  }
  get query(){
    return this.reportForm.get('query');
  }
  get title(){
    return this.reportForm.get('title');
  }

}
