import { Component, OnInit } from '@angular/core';
import { cloneDeep } from '@apollo/client/utilities';
import { DialogService, DynamicDialogRef } from 'primeng/dynamicdialog';
import { SourcePaths } from 'src/app/models/sourcepaths';
import { SourcepathService } from 'src/app/services/sourcepath/sourcepath.service';
import { AddSourcepathComponent } from '../../dialog/admin/add-sourcepath/add-sourcepath.component';
import { SourcepathDetailsComponent } from '../../dialog/admin/sourcepath-details/sourcepath-details.component';
import { ConfirmationDialogComponent } from '../../dialog/shared/confirmation-dialog/confirmation-dialog.component';

@Component({
  selector: 'app-sourcepath',
  templateUrl: './sourcepath.component.html',
  styleUrls: ['./sourcepath.component.scss']
})
export class SourcepathComponent implements OnInit {

  sourcePaths:SourcePaths[];
  loading:boolean;

  ref: DynamicDialogRef;

  constructor(private sourcePathService:SourcepathService, private dialogService:DialogService) { }

  ngOnInit(): void {
    this.getAllSourcePaths();
  }

  getAllSourcePaths(){
    this.loading = true;

    this.sourcePathService.getAllSourcePaths().subscribe({
      next:(response:SourcePaths[])=>{
          this.sourcePaths = cloneDeep(response);
          this.loading = false;
      },
      error:(error:any)=>{
          console.log(error);
      }
    })
  }

  deleteSourcePath(sourcePathId:string){
    this.sourcePathService.deleteSourcePath(sourcePathId).subscribe({
      next: (result: any) => {
        console.log(result);
        this.getAllSourcePaths();
      },
      error: (error: any) => {
        console.log(error);
      }
    });
  }

  showAddSourcePathDialog(){
    this.ref = this.dialogService.open(AddSourcepathComponent,{
        header: 'Add Source Path',
        baseZIndex: 10000,
        contentStyle: {"max-height": "650px", "width":"40vw", "min-width":"400px", "max-width":"600px", "overflow": "auto"},
    });

    this.ref.onClose.subscribe((success:boolean)=>{
        if(success){
          this.getAllSourcePaths();
        }
    });
  }

  showSourcePathDetailsDialog(sourcePath:SourcePaths){
    this.ref = this.dialogService.open(SourcepathDetailsComponent,{
        header: 'Source Path Details',
        data:{
          sourcePathData:sourcePath
        },
        baseZIndex: 10000,
        contentStyle: {"max-height": "650px", "width":"40vw", "min-width":"350px", "max-width":"500px","overflow": "auto"},
    });

    this.ref.onClose.subscribe((success:boolean)=>{
      if(success){
        this.getAllSourcePaths();
      }
    });
  }

  showConfirmationDialog(title:string, message:string, action:string, sourcePathId:string){
    this.ref = this.dialogService.open(ConfirmationDialogComponent, {
      header: title,
      data: {
        message: message,
      },
      baseZIndex: 10000,
      contentStyle: {"max-height": "500px", "width":"40vw", "min-width":"350px", "max-width":"500px", "overflow": "auto"},
    });

    this.ref.onClose.subscribe((confirm:boolean) =>{
        if (confirm) {
          if(action == 'delete'){
            this.deleteSourcePath(sourcePathId);
          }
        }
    });
  }
}
