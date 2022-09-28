import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule } from '@angular/common/http'
import { WebdatarocksPivotModule } from 'ng-webdatarocks';
import { ReactiveFormsModule } from '@angular/forms';

import { MenuModule } from 'primeng/menu';
import { MenubarModule } from 'primeng/menubar';
import { TabViewModule } from 'primeng/tabview';
import { DialogService, DynamicDialogModule } from 'primeng/dynamicdialog';
import { InputTextModule } from 'primeng/inputtext';
import { RadioButtonModule } from 'primeng/radiobutton';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { PivotTableComponent } from './components/pivot-table/pivot-table.component';
import { ExportDialogComponent } from './components/dialog/export-dialog/export-dialog.component';

@NgModule({
  declarations: [
    AppComponent,
    PivotTableComponent,
    ExportDialogComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule,
    WebdatarocksPivotModule,
    MenuModule,
    MenubarModule,
    TabViewModule,
    DynamicDialogModule,
    InputTextModule,
    RadioButtonModule
  ],
  providers: [DialogService],
  bootstrap: [AppComponent]
})
export class AppModule { }
