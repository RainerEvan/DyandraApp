import { Component, OnInit } from '@angular/core';
import { DialogService, DynamicDialogRef } from 'primeng/dynamicdialog';
import { SourcePaths } from 'src/app/models/sourcepaths';
import { SourcepathService } from 'src/app/services/sourcepath/sourcepath.service';
import { AddSourcepathComponent } from '../../dialog/admin/add-sourcepath/add-sourcepath.component';

@Component({
  selector: 'app-sourcepath',
  templateUrl: './sourcepath.component.html',
  styleUrls: ['./sourcepath.component.scss']
})
export class SourcepathComponent implements OnInit {

  sourcePaths:SourcePaths[];
  loading:boolean;

  ref: DynamicDialogRef;

  constructor(private sourcePathervice:SourcepathService, private dialogService:DialogService) { }

  ngOnInit(): void {
    this.getAllSourcePaths();
  }

  getAllSourcePaths(){
    this.loading = true;

    this.sourcePathervice.getAllSourcePaths().subscribe({
      next:(response:SourcePaths[])=>{
          this.sourcePaths = response;
          this.loading = false;
      },
      error:(error:any)=>{
          console.log(error);
      }
    })
  }

  showAddSourcePathDialog(){
    this.ref = this.dialogService.open(AddSourcepathComponent,{
        header: 'Add Source Path',
        baseZIndex: 10000,
        contentStyle: {"max-height": "650px", "min-width":"30vw","overflow": "auto"},
    });

    this.ref.onClose.subscribe((success:boolean)=>{
        if(success){
          this.getAllSourcePaths();
        }
    });
  }

}
