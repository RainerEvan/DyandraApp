import { Component, OnInit } from '@angular/core';
import { DialogService, DynamicDialogRef } from 'primeng/dynamicdialog';
import { Connections } from 'src/app/models/connections';
import { ConnectionService } from 'src/app/services/connection/connection.service';
import { AddConnectionComponent } from '../../dialog/admin/add-connection/add-connection.component';

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
          this.connections = response;
          this.loading = false;
      },
      error:(error:any)=>{
          console.log(error);
      }
    })
  }

  showAddConnectionDialog(){
    this.ref = this.dialogService.open(AddConnectionComponent,{
        header: 'Add Connection',
        baseZIndex: 10000,
        contentStyle: {"max-height": "650px", "min-width":"30vw","overflow": "auto"},
    });

    this.ref.onClose.subscribe((success:boolean)=>{
        if(success){
          this.getAllConnections();
        }
    });
  }

}
