import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http'
import { WebdatarocksPivotModule } from 'ng-webdatarocks';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { PivotTableComponent } from './components/pivot-table/pivot-table.component';

@NgModule({
  declarations: [
    AppComponent,
    PivotTableComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    WebdatarocksPivotModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
