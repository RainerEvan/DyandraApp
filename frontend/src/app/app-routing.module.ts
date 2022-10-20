import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminHomeComponent } from './components/admin/admin-home/admin-home.component';
import { AdminPageComponent } from './components/admin/admin-page/admin-page.component';
import { ApplicationComponent } from './components/admin/application/application.component';
import { ConnectionComponent } from './components/admin/connection/connection.component';
import { ReportComponent } from './components/admin/report/report.component';
import { SourcepathComponent } from './components/admin/sourcepath/sourcepath.component';

const routes: Routes = [
  {
    path:'',
    redirectTo:'admin',
    pathMatch:'full'
  },
  {
    path:'admin',
    component: AdminPageComponent,
    children:[
      {
        path:'',
        redirectTo: 'home',
        pathMatch: 'full'
      },
      {
        path:'home',
        component: AdminHomeComponent,
      },
      {
        path:'application',
        component: ApplicationComponent,
      },
      {
        path:'connection',
        component: ConnectionComponent,
      },
      {
        path:'sourcepath',
        component: SourcepathComponent,
      },
      {
        path:'report',
        component: ReportComponent,
      },
    ]
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
