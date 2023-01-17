import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ConfirmationDialogComponent } from './components/confirmation-dialog/confirmation-dialog.component';
import { ResultDialogComponent } from './components/result-dialog/result-dialog.component';

import { DynamicDialogModule } from 'primeng/dynamicdialog';

@NgModule({
  declarations: [
    ConfirmationDialogComponent,
    ResultDialogComponent
  ],
  imports: [
    CommonModule,
    DynamicDialogModule,
  ],
  exports: [
    ConfirmationDialogComponent,
    ResultDialogComponent
  ]
})
export class SharedModule { }
