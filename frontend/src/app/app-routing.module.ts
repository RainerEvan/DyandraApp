import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminHomeComponent } from './components/admin/admin-home/admin-home.component';
import { AdminPageComponent } from './components/admin/admin-page/admin-page.component';
import { ApplicationComponent } from './components/admin/application/application.component';
import { ConnectionComponent } from './components/admin/connection/connection.component';
import { LoginComponent } from './components/admin/login/login.component';
import { ProfileComponent } from './components/admin/profile/profile.component';
import { ReportComponent } from './components/admin/report/report.component';
import { SourcepathComponent } from './components/admin/sourcepath/sourcepath.component';
import { PivotTableComponent } from './components/pivot-table/pivot-table.component';
import { AccountAuthGuard } from './utils/account-auth-guard';

const routes: Routes = [
  {
    path:'',
    redirectTo:'pivot',
    pathMatch:'full'
  },
  {
    path:'admin',
    component: AdminHomeComponent,
    children:[
      {
        path:'',
        component: AdminPageComponent,
        canActivate:[AccountAuthGuard],
        children:[
          {
            path:'',
            redirectTo: 'application',
            pathMatch: 'full'
          },
          {
            path:'application',
            component: ApplicationComponent,
            canActivate:[AccountAuthGuard],
          },
          {
            path:'connection',
            component: ConnectionComponent,
            canActivate:[AccountAuthGuard],
          },
          {
            path:'sourcepath',
            component: SourcepathComponent,
            canActivate:[AccountAuthGuard],
          },
          {
            path:'report',
            component: ReportComponent,
            canActivate:[AccountAuthGuard],
          },
          {
            path:'profile',
            component: ProfileComponent,
            canActivate:[AccountAuthGuard],
          },
        ]
      },
      {
        path:'login',
        component: LoginComponent,
      }
    ]
  },
  {
    path:'pivot',
    component: PivotTableComponent,
  },
  {
    path:'pivot/:id',
    component: PivotTableComponent,
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
