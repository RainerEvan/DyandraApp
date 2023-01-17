import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { GraphQLModule } from './graphql.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { DatePipe, HashLocationStrategy, LocationStrategy } from '@angular/common';
import { JwtInterceptor } from './utils/jwt-interceptor/jwt.interceptor';
import { ErrorInterceptor } from './utils/error-interceptor/error.interceptor';

import { DialogService } from 'primeng/dynamicdialog';

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    HttpClientModule,
    GraphQLModule,
    FormsModule,
    ReactiveFormsModule,
  ],
  providers: [
    { 
      provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true 
    },
    { 
      provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true 
    },
    {
      provide: LocationStrategy, useClass:HashLocationStrategy
    },
    DialogService,
    DatePipe
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
