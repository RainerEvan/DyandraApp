import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ShellComponent } from './components/shell/shell.component';
import { LoginComponent } from './components/login/login.component';
import { AuthGuard } from 'src/app/utils/auth-guard/auth.guard';

const routes: Routes = [
  {
    path: '',
    redirectTo: 'application',
    pathMatch: 'full'
  },
  {
    path:'login',
    component: LoginComponent,
  },
  {
    path: '',
    component:ShellComponent,
    children: [
      {
        path: 'application',
        loadChildren: () => import('./modules/application/application.module').then((m) => m.ApplicationModule),
        canActivate:[AuthGuard],
      },
      {
        path: 'connection',
        loadChildren: () => import('./modules/connection/connection.module').then((m) => m.ConnectionModule),
        canActivate:[AuthGuard],
      },
      {
        path: 'sourcepath',
        loadChildren: () => import('./modules/source-path/source-path.module').then((m) => m.SourcePathModule),
        canActivate:[AuthGuard],
      },
      {
        path: 'report',
        loadChildren: () => import('./modules/report/report.module').then((m) => m.ReportModule),
        canActivate:[AuthGuard],
      },
      {
        path: 'profile',
        loadChildren: () => import('./modules/profile/profile.module').then((m) => m.ProfileModule),
        canActivate:[AuthGuard],
      },
    ]
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdminRoutingModule { }
