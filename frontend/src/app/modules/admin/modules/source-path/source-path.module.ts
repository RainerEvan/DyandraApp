import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { SourcePathRoutingModule } from './source-path-routing.module';
import { SourcePathListComponent } from './components/source-path-list/source-path-list.component';
import { SourcePathDetailComponent } from './components/source-path-detail/source-path-detail.component';
import { AddSourcePathComponent } from './components/add-source-path/add-source-path.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { SharedModule } from 'src/app/modules/shared/shared.module';

import { InputTextModule } from 'primeng/inputtext';
import { InputTextareaModule } from 'primeng/inputtextarea';
import { DropdownModule } from 'primeng/dropdown';
import { TableModule } from 'primeng/table';


@NgModule({
  declarations: [
    SourcePathListComponent,
    SourcePathDetailComponent,
    AddSourcePathComponent
  ],
  imports: [
    CommonModule,
    SourcePathRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    SharedModule,
    InputTextModule,
    InputTextareaModule,
    DropdownModule,
    TableModule,
  ]
})
export class SourcePathModule { }
