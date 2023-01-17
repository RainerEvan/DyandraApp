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
  showPassword: boolean = false;
  
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
      name: [null, [Validators.required]],
      path: [null, [Validators.required]],
      username: [null],
      password: [null],
    });
    this.getAllconnections();
  }

  get connectionId(){
    return this.sourcePathForm.get('connectionId');
  }
  get name(){
    return this.sourcePathForm.get('name');
  }
  get path(){
    return this.sourcePathForm.get('path');
  }
  get username(){
    return this.sourcePathForm.get('username');
  }
  get password(){
    return this.sourcePathForm.get('password');
  }

}
