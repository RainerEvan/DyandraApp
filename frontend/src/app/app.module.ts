import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule } from '@angular/common/http'
import { WebdatarocksPivotModule } from 'ng-webdatarocks';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
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
import { HomeComponent } from './components/home/home.component';
import { ExportDialogComponent } from './components/dialog/pivot-table/export-dialog/export-dialog.component';
import { OpenDialogComponent } from './components/dialog/pivot-table/open-dialog/open-dialog.component';
import { SaveDialogComponent } from './components/dialog/pivot-table/save-dialog/save-dialog.component';
import { OptionsDialogComponent } from './components/dialog/pivot-table/options-dialog/options-dialog.component';
import { FormatDialogComponent } from './components/dialog/pivot-table/format-dialog/format-dialog.component';
import { DropdownModule } from 'primeng/dropdown';
import { ApplicationComponent } from './components/admin/application/application.component';
import { ConnectionComponent } from './components/admin/connection/connection.component';
import { SourcepathComponent } from './components/admin/sourcepath/sourcepath.component';
import { ReportComponent } from './components/admin/report/report.component';
import { AdminPageComponent } from './components/admin/admin-page/admin-page.component';
import { AdminHeaderComponent } from './components/admin/admin-header/admin-header.component';
import { AdminMenuComponent } from './components/admin/admin-menu/admin-menu.component';
import { AdminHomeComponent } from './components/admin/admin-home/admin-home.component';

@NgModule({
  declarations: [
    AppComponent,
    PivotTableComponent,
    HomeComponent,
    ExportDialogComponent,
    OpenDialogComponent,
    SaveDialogComponent,
    OptionsDialogComponent,
    FormatDialogComponent,
    AdminPageComponent,
    ApplicationComponent,
    ConnectionComponent,
    SourcepathComponent,
    ReportComponent,
    AdminHeaderComponent,
    AdminMenuComponent,
    AdminHomeComponent,
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    WebdatarocksPivotModule,
    GraphQLModule,
    MenuModule,
    MenubarModule,
    TabViewModule,
    DynamicDialogModule,
    InputTextModule,
    RadioButtonModule,
    DropdownModule,
    TableModule,
  ],
  providers: [DialogService],
  bootstrap: [AppComponent]
})
export class AppModule { }
