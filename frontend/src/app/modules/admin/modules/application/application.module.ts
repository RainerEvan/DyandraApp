import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ApplicationRoutingModule } from './application-routing.module';
import { ApplicationListComponent } from './components/application-list/application-list.component';
import { ApplicationDetailComponent } from './components/application-detail/application-detail.component';
import { AddApplicationComponent } from './components/add-application/add-application.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { SharedModule } from 'src/app/modules/shared/shared.module';

import { InputTextModule } from 'primeng/inputtext';
import { TableModule } from 'primeng/table';

@NgModule({
  declarations: [
    ApplicationListComponent,
    ApplicationDetailComponent,
    AddApplicationComponent
  ],
  imports: [
    CommonModule,
    ApplicationRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    SharedModule,
    InputTextModule,
    TableModule,
  ]
})
export class ApplicationModule { }
