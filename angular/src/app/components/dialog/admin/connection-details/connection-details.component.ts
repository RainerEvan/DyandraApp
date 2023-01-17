import { DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { DynamicDialogConfig, DynamicDialogRef } from 'primeng/dynamicdialog';
import { Connections } from 'src/app/models/connections';

@Component({
  selector: 'app-connection-details',
  templateUrl: './connection-details.component.html',
  styleUrls: ['./connection-details.component.scss']
})
export class ConnectionDetailsComponent implements OnInit {

  connectionForm: FormGroup;
  connection: Connections;
  
  constructor(public ref: DynamicDialogRef, public config: DynamicDialogConfig, private datePipe:DatePipe,private formBuilder: FormBuilder) { }

  ngOnInit(): void {
    this.connection = this.config.data.connectionData;
    this.generateConnectionForm();
  }

  generateConnectionForm(){
    this.connectionForm = this.formBuilder.group({
      name: [{value:this.connection.name, disabled:true}],
      application: [{value:this.connection.application.name, disabled:true}],
      method: [{value:this.connection.method.name, disabled:true}],
      dateCreated: [{value:this.datePipe.transform(this.connection.createdAt,'dd/MM/yyyy'), disabled:true}],
    });
  }

  get name(){
    return this.connectionForm.get('name');
  }
  get applicationId(){
    return this.connectionForm.get('application');
  }
  get methodName(){
    return this.connectionForm.get('method');
  }
  get dateCreated(){
    return this.connectionForm.get('dateCreated');
  }

}
