import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule } from '@angular/common/http'
import { WebdatarocksPivotModule } from 'ng-webdatarocks';
import { ReactiveFormsModule } from '@angular/forms';
import { GraphQLModule } from './graphql.module';

import { MenuModule } from 'primeng/menu';
import { MenubarModule } from 'primeng/menubar';
import { TabViewModule } from 'primeng/tabview';
import { DialogService, DynamicDialogModule } from 'primeng/dynamicdialog';
import { InputTextModule } from 'primeng/inputtext';
import { RadioButtonModule } from 'primeng/radiobutton';
import { TableModule } from 'primeng/table';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { PivotTableComponent } from './components/pivot-table/pivot-table.component';
import { ExportDialogComponent } from './components/dialog/export-dialog/export-dialog.component';
import { OpenDialogComponent } from './components/dialog/open-dialog/open-dialog.component';
import { SaveDialogComponent } from './components/dialog/save-dialog/save-dialog.component';
import { OptionsDialogComponent } from './components/dialog/options-dialog/options-dialog.component';

@NgModule({
  declarations: [
    AppComponent,
    PivotTableComponent,
    ExportDialogComponent,
    OpenDialogComponent,
    SaveDialogComponent,
    OptionsDialogComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule,
    WebdatarocksPivotModule,
    GraphQLModule,
    MenuModule,
    MenubarModule,
    TabViewModule,
    DynamicDialogModule,
    InputTextModule,
    RadioButtonModule,
    TableModule,
  ],
  providers: [DialogService],
  bootstrap: [AppComponent]
})
export class AppModule { }
