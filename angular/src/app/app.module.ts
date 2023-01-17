import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http'
import { WebdatarocksPivotModule } from 'ng-webdatarocks';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { GraphQLModule } from './graphql.module';

import { MenuModule } from 'primeng/menu';
import { MenubarModule } from 'primeng/menubar';
import { TabViewModule } from 'primeng/tabview';
import { DialogService, DynamicDialogModule } from 'primeng/dynamicdialog';
import { InputTextModule } from 'primeng/inputtext';
import { InputTextareaModule } from 'primeng/inputtextarea';
import { RadioButtonModule } from 'primeng/radiobutton';
import { TableModule } from 'primeng/table';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { PivotTableComponent } from './components/pivot-table/pivot-table.component';
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
import { AddApplicationComponent } from './components/dialog/admin/add-application/add-application.component';
import { AddConnectionComponent } from './components/dialog/admin/add-connection/add-connection.component';
import { AddSourcepathComponent } from './components/dialog/admin/add-sourcepath/add-sourcepath.component';
import { AddReportComponent } from './components/dialog/admin/add-report/add-report.component';
import { ConfirmationDialogComponent } from './components/dialog/shared/confirmation-dialog/confirmation-dialog.component';
import { ApplicationDetailsComponent } from './components/dialog/admin/application-details/application-details.component';
import { ConnectionDetailsComponent } from './components/dialog/admin/connection-details/connection-details.component';
import { SourcepathDetailsComponent } from './components/dialog/admin/sourcepath-details/sourcepath-details.component';
import { ReportDetailsComponent } from './components/dialog/admin/report-details/report-details.component';
import { DatePipe } from '@angular/common';
import { ProfileComponent } from './components/admin/profile/profile.component';
import { LoginComponent } from './components/admin/login/login.component';
import { AdminHomeComponent } from './components/admin/admin-home/admin-home.component';
import { AccountJwtInterceptor } from './utils/account-jwt-interceptor';
import { ErrorInterceptor } from './utils/error-interceptor';

@NgModule({
  declarations: [
    AppComponent,
    PivotTableComponent,
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
    AddApplicationComponent,
    AddConnectionComponent,
    AddSourcepathComponent,
    AddReportComponent,
    ConfirmationDialogComponent,
    ApplicationDetailsComponent,
    ConnectionDetailsComponent,
    SourcepathDetailsComponent,
    ReportDetailsComponent,
    ProfileComponent,
    LoginComponent,
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
    InputTextareaModule,
    RadioButtonModule,
    DropdownModule,
    TableModule,
  ],
  providers: [
    { 
      provide: HTTP_INTERCEPTORS, useClass: AccountJwtInterceptor, multi: true 
    },
    { 
      provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true 
    },
    DialogService,
    DatePipe
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
