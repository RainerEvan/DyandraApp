import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { DialogService, DynamicDialogRef } from 'primeng/dynamicdialog';
import { Connections } from 'src/app/models/connections';
import { ResultDialogComponent } from 'src/app/modules/shared/components/result-dialog/result-dialog.component';
import { ConnectionService } from 'src/app/services/connection/connection.service';
import { SourcepathService } from 'src/app/services/source-path/source-path.service';

@Component({
  selector: 'app-add-source-path',
  templateUrl: './add-source-path.component.html',
  styleUrls: ['./add-source-path.component.css']
})
export class AddSourcePathComponent implements OnInit {

  sourcePathForm: FormGroup;
  isSourcePathFormSubmitted: boolean = false;
  connections: Connections[];
  showPassword: boolean = false;
  
  constructor(public ref: DynamicDialogRef, private dialogService:DialogService, private sourcePathService: SourcepathService, private connectionService:ConnectionService , private formBuilder: FormBuilder) { }

  ngOnInit(): void {
    this.generateSourcePathForm();
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

  public addSourcePath(): void{
    if(this.sourcePathForm.valid){
      const sourcePath = this.sourcePathForm.value;

      this.sourcePathService.addSourcePath(sourcePath).subscribe({
        next: (result: any) => {
          this.isSourcePathFormSubmitted = true;
          this.ref.close(this.isSourcePathFormSubmitted);
          this.showResultDialog("Success","Source Path has been created successfully");
        },
        error: (error: any) => {
          this.isSourcePathFormSubmitted = false;
          this.ref.close(this.isSourcePathFormSubmitted);
          this.showResultDialog("Failed","There was a problem, try again later");
        }
      });
    } 
  }

  generateSourcePathForm(){
    this.sourcePathForm = this.formBuilder.group({
      connectionId: [null, [Validators.required]],
      name: [null, [Validators.required]],
      path: [null, [Validators.required]],
      username: [null],
      password: [null],
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
