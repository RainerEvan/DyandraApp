import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { PivotRoutingModule } from './pivot-routing.module';
import { PivotTableComponent } from './components/pivot-table/pivot-table.component';
import { ExportDialogComponent } from './components/export-dialog/export-dialog.component';
import { FormatDialogComponent } from './components/format-dialog/format-dialog.component';
import { OpenDialogComponent } from './components/open-dialog/open-dialog.component';
import { OptionsDialogComponent } from './components/options-dialog/options-dialog.component';
import { SaveDialogComponent } from './components/save-dialog/save-dialog.component';
import { WebdatarocksPivotModule } from 'ng-webdatarocks';

import { DynamicDialogModule } from 'primeng/dynamicdialog';
import { MenuModule } from 'primeng/menu';
import { MenubarModule } from 'primeng/menubar';
import { InputTextModule } from 'primeng/inputtext';
import { RadioButtonModule } from 'primeng/radiobutton';
import { DropdownModule } from 'primeng/dropdown';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { TableModule } from 'primeng/table';
import { TooltipModule } from 'primeng/tooltip';


@NgModule({
  declarations: [
    PivotTableComponent,
    ExportDialogComponent,
    FormatDialogComponent,
    OpenDialogComponent,
    OptionsDialogComponent,
    SaveDialogComponent
  ],
  imports: [
    CommonModule,
    PivotRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    WebdatarocksPivotModule,
    TableModule,
    DynamicDialogModule,
    MenuModule,
    MenubarModule,
    InputTextModule,
    RadioButtonModule,
    DropdownModule,
    TooltipModule
  ]
})
export class PivotModule { }
