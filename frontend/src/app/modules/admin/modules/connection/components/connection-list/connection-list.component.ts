import { Component, OnInit } from '@angular/core';
import { cloneDeep } from '@apollo/client/utilities';
import { DialogService, DynamicDialogRef } from 'primeng/dynamicdialog';
import { Connections } from 'src/app/models/connections';
import { ConnectionService } from 'src/app/services/connection/connection.service';
import { AddConnectionComponent } from '../add-connection/add-connection.component';
import { ConnectionDetailComponent } from '../connection-detail/connection-detail.component';
import { ConfirmationDialogComponent } from 'src/app/modules/shared/components/confirmation-dialog/confirmation-dialog.component';

@Component({
  selector: 'app-connection-list',
  templateUrl: './connection-list.component.html',
  styleUrls: ['./connection-list.component.css']
})
export class ConnectionListComponent implements OnInit {

  connections:Connections[];
  loading:boolean;

  ref: DynamicDialogRef;

  constructor(private connectionService:ConnectionService, private dialogService:DialogService) { }

  ngOnInit(): void {
    this.getAllConnections();
  }

  getAllConnections(){
    this.loading = true;

    this.connectionService.getAllConnections().subscribe({
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
    this.connectionService.deleteConnection(connectionId).subscribe({
      next: (result: any) => {
        console.log(result);
        this.getAllConnections();
      },
      error: (error: any) => {
        console.log(error);
      }
    });
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

  showConnectionDetailsDialog(connection:Connections){
    this.ref = this.dialogService.open(ConnectionDetailComponent,{
        header: 'Connection Details',
        data:{
          connectionData:connection
        },
        footer:" ",
        baseZIndex: 10000,
        contentStyle: {"max-height": "650px", "width":"40vw", "min-width":"350px", "max-width":"500px","overflow": "auto"},
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
