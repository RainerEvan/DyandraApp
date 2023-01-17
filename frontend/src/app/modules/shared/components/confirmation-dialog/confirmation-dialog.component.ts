import { Component, OnInit } from '@angular/core';
import { DynamicDialogConfig, DynamicDialogRef } from 'primeng/dynamicdialog';

@Component({
  selector: 'app-confirmation-dialog',
  templateUrl: './confirmation-dialog.component.html',
  styleUrls: ['./confirmation-dialog.component.css']
})
export class ConfirmationDialogComponent implements OnInit {

  confirm:boolean;
  message:string;

  constructor(public ref: DynamicDialogRef, public config: DynamicDialogConfig) { }

  ngOnInit(): void {
    this.message=this.config.data.message;
  }

  confirmAction(){
    this.confirm=true;
    this.ref.close(this.confirm);
  }

  cancelAction(){
    this.confirm=false;
    this.ref.close(this.confirm);
  }

}
