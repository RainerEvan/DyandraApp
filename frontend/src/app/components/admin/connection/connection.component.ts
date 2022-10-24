import { Component, OnInit } from '@angular/core';
import { cloneDeep } from '@apollo/client/utilities';
import { DialogService, DynamicDialogRef } from 'primeng/dynamicdialog';
import { Connections } from 'src/app/models/connections';
import { ConnectionService } from 'src/app/services/connection/connection.service';
import { AddConnectionComponent } from '../../dialog/admin/add-connection/add-connection.component';
import { ConfirmationDialogComponent } from '../../dialog/shared/confirmation-dialog/confirmation-dialog.component';

@Component({
  selector: 'app-connection',
  templateUrl: './connection.component.html',
  styleUrls: ['./connection.component.scss']
})
export class ConnectionComponent implements OnInit {

  connections:Connections[];
  loading:boolean;

  ref: DynamicDialogRef;

  constructor(private connectionservice:ConnectionService, private dialogService:DialogService) { }

  ngOnInit(): void {
    this.getAllConnections();
  }

  getAllConnections(){
    this.loading = true;

    this.connectionservice.getAllConnections().subscribe({
      next:(response:Connections[])=>{
          this.connections = cloneDeep(response);
          this.loading = false;
      },
      error:(error:any)=>{
          console.log(error);
      }
    })
  }

  deleteConnection(connectionId:string){
    
  }

  showAddConnectionDialog(){
    this.ref = this.dialogService.open(AddConnectionComponent,{
        header: 'Add Connection',
        baseZIndex: 10000,
        contentStyle: {"max-height": "650px","width":"40vw", "min-width":"350px", "max-width":"500px","overflow": "auto"},
    });

    this.ref.onClose.subscribe((success:boolean)=>{
        if(success){
          this.getAllConnections();
        }
    });
  }
  showConfirmationDialog(title:string, message:string, action:string, connectionId:string){
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
            this.deleteConnection(connectionId);
          }
        }
    });
  }
}
