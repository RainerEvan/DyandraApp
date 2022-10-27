import { DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { DynamicDialogConfig, DynamicDialogRef } from 'primeng/dynamicdialog';
import { SourcePaths } from 'src/app/models/sourcepaths';
import { SourcepathService } from 'src/app/services/sourcepath/sourcepath.service';

@Component({
  selector: 'app-sourcepath-details',
  templateUrl: './sourcepath-details.component.html',
  styleUrls: ['./sourcepath-details.component.scss']
})
export class SourcepathDetailsComponent implements OnInit {

  sourcePathForm: FormGroup;
  isSourcePathFormSubmitted: boolean = false;
  sourcePath: SourcePaths;
  
  constructor(public ref: DynamicDialogRef, public config: DynamicDialogConfig, private sourcePathService: SourcepathService, private datePipe:DatePipe, private formBuilder: FormBuilder) { }

  ngOnInit(): void {
    this.sourcePath = this.config.data.sourcePathData;
    console.log(this.sourcePath);
    this.generateSourcePathForm();
  }

  public editSourcePath(): void{
    if(this.sourcePathForm.valid){
      const sourcePathId = this.sourcePath.id;
      const sourcePath = this.sourcePathForm.value;

      this.sourcePathService.editSourcePath(sourcePathId,sourcePath).subscribe({
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
      connection: [{value:this.sourcePath.connection.name, disabled:true}],
      name: [{value:this.sourcePath.name, disabled:true}],
      dateCreated: [{value:this.datePipe.transform(this.sourcePath.createdAt,'dd/MM/yyyy'), disabled:true}],
      path: [this.sourcePath.path],
      username: [this.sourcePath.username],
      password: [this.sourcePath.password],
    });
  }

  get connection(){
    return this.sourcePathForm.get('connection');
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
  get dateCreated(){
    return this.sourcePathForm.get('dateCreated');
  }
}
