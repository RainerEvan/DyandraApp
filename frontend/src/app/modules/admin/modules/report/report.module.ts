import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ReportRoutingModule } from './report-routing.module';
import { ReportListComponent } from './components/report-list/report-list.component';
import { ReportDetailComponent } from './components/report-detail/report-detail.component';
import { AddReportComponent } from './components/add-report/add-report.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { SharedModule } from 'src/app/modules/shared/shared.module';

import { InputTextModule } from 'primeng/inputtext';
import { InputTextareaModule } from 'primeng/inputtextarea';
import { DropdownModule } from 'primeng/dropdown';
import { TableModule } from 'primeng/table';


@NgModule({
  declarations: [
    ReportListComponent,
    ReportDetailComponent,
    AddReportComponent
  ],
  imports: [
    CommonModule,
    ReportRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    SharedModule,
    InputTextModule,
    InputTextareaModule,
    DropdownModule,
    TableModule,
  ]
})
export class ReportModule { }
