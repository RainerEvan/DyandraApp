import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { DynamicDialogRef } from 'primeng/dynamicdialog';
import { Connections } from 'src/app/models/connections';
import { ConnectionService } from 'src/app/services/connection/connection.service';
import { SourcepathService } from 'src/app/services/sourcepath/sourcepath.service';

@Component({
  selector: 'app-add-sourcepath',
  templateUrl: './add-sourcepath.component.html',
  styleUrls: ['./add-sourcepath.component.scss']
})
export class AddSourcepathComponent implements OnInit {

  sourcePathForm: FormGroup;
  isSourcePathFormSubmitted: boolean = false;
  connections: Connections[];
  
  constructor(public ref: DynamicDialogRef,private sourcePathService: SourcepathService, private connectionService:ConnectionService , private formBuilder: FormBuilder) { }

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
          console.log(result);
        },
        error: (error: any) => {
          this.isSourcePathFormSubmitted = false;
          this.ref.close(this.isSourcePathFormSubmitted);
          console.log(error);
        }
      });
    } 
  }

  generateSourcePathForm(){
    this.sourcePathForm = this.formBuilder.group({
      connectionId: [null, [Validators.required]],
      path: [null, [Validators.required]],
    });
    this.getAllconnections();
  }

  get connectionId(){
    return this.sourcePathForm.get('connectionId');
  }
  get path(){
    return this.sourcePathForm.get('path');
  }

}
