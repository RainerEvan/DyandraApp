import { Component, OnInit } from '@angular/core';
import { cloneDeep } from '@apollo/client/utilities';
import { DialogService, DynamicDialogRef } from 'primeng/dynamicdialog';
import { SourcePaths } from 'src/app/models/sourcepaths';
import { SourcepathService } from 'src/app/services/source-path/source-path.service';
import { AddSourcePathComponent } from '../add-source-path/add-source-path.component';
import { SourcePathDetailComponent } from '../source-path-detail/source-path-detail.component';
import { ConfirmationDialogComponent } from 'src/app/modules/shared/components/confirmation-dialog/confirmation-dialog.component';

@Component({
  selector: 'app-source-path-list',
  templateUrl: './source-path-list.component.html',
  styleUrls: ['./source-path-list.component.css']
})
export class SourcePathListComponent implements OnInit {

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
    this.ref = this.dialogService.open(AddSourcePathComponent,{
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
    this.ref = this.dialogService.open(SourcePathDetailComponent,{
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
