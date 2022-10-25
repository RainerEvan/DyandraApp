import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminPageComponent } from './components/admin/admin-page/admin-page.component';
import { ApplicationComponent } from './components/admin/application/application.component';
import { ConnectionComponent } from './components/admin/connection/connection.component';
import { ReportComponent } from './components/admin/report/report.component';
import { SourcepathComponent } from './components/admin/sourcepath/sourcepath.component';
import { PivotTableComponent } from './components/pivot-table/pivot-table.component';

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
        redirectTo: 'application',
        pathMatch: 'full'
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
  {
    path:'pivot',
    component: PivotTableComponent,
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
