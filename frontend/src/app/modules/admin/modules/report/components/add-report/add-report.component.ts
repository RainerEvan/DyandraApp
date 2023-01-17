import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { DialogService, DynamicDialogRef } from 'primeng/dynamicdialog';
import { Connections } from 'src/app/models/connections';
import { SourcePaths } from 'src/app/models/sourcepaths';
import { ResultDialogComponent } from 'src/app/modules/shared/components/result-dialog/result-dialog.component';
import { ConnectionService } from 'src/app/services/connection/connection.service';
import { ReportService } from 'src/app/services/report/report.service';
import { SourcepathService } from 'src/app/services/source-path/source-path.service';

@Component({
  selector: 'app-add-report',
  templateUrl: './add-report.component.html',
  styleUrls: ['./add-report.component.css']
})
export class AddReportComponent implements OnInit {

  reportForm: FormGroup;
  isReportFormSubmitted: boolean = false;
  connections: Connections[];
  sourcePaths: SourcePaths[];
  
  constructor(public ref: DynamicDialogRef, private dialogService:DialogService, private reportService:ReportService, private sourcePathService: SourcepathService, private connectionService:ConnectionService, private formBuilder: FormBuilder) { }

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
          this.showResultDialog("Success","Report has been created successfully");
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
      connectionId: [null, [Validators.required]],
      sourcePathId: [{value:null, disabled:true}, [Validators.required]],
      query: [null],
      title: [null, [Validators.required]],
    });
    this.getAllconnections();
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
