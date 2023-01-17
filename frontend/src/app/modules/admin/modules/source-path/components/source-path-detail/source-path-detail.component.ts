import { DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { DialogService, DynamicDialogConfig, DynamicDialogRef } from 'primeng/dynamicdialog';
import { SourcePaths } from 'src/app/models/sourcepaths';
import { ResultDialogComponent } from 'src/app/modules/shared/components/result-dialog/result-dialog.component';
import { SourcepathService } from 'src/app/services/source-path/source-path.service';

@Component({
  selector: 'app-source-path-detail',
  templateUrl: './source-path-detail.component.html',
  styleUrls: ['./source-path-detail.component.css']
})
export class SourcePathDetailComponent implements OnInit {

  sourcePathForm: FormGroup;
  isSourcePathFormSubmitted: boolean = false;
  sourcePath: SourcePaths;
  showPassword: boolean = false;
  
  constructor(public ref: DynamicDialogRef, public config: DynamicDialogConfig, private dialogService:DialogService, private sourcePathService: SourcepathService, private datePipe:DatePipe, private formBuilder: FormBuilder) { }

  ngOnInit(): void {
    this.sourcePath = this.config.data.sourcePathData;
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
          this.showResultDialog("Success","Source Path has been updated successfully");
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
      connection: [{value:this.sourcePath.connection.name, disabled:true}],
      name: [{value:this.sourcePath.name, disabled:true}],
      dateCreated: [{value:this.datePipe.transform(this.sourcePath.createdAt,'dd/MM/yyyy'), disabled:true}],
      path: [this.sourcePath.path],
      username: [this.sourcePath.username],
      password: [this.sourcePath.password],
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
