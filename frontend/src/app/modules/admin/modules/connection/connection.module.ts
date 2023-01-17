import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ConnectionRoutingModule } from './connection-routing.module';
import { ConnectionListComponent } from './components/connection-list/connection-list.component';
import { ConnectionDetailComponent } from './components/connection-detail/connection-detail.component';
import { AddConnectionComponent } from './components/add-connection/add-connection.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { SharedModule } from 'src/app/modules/shared/shared.module';

import { InputTextModule } from 'primeng/inputtext';
import { DropdownModule } from 'primeng/dropdown';
import { TableModule } from 'primeng/table';


@NgModule({
  declarations: [
    ConnectionListComponent,
    ConnectionDetailComponent,
    AddConnectionComponent
  ],
  imports: [
    CommonModule,
    ConnectionRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    SharedModule,
    InputTextModule,
    DropdownModule,
    TableModule,
  ]
})
export class ConnectionModule { }
