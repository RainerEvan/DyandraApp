import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from './utils/auth-guard/auth.guard';

const routes: Routes = [
  {
    path: '',
    redirectTo: 'report',
    pathMatch: 'full'
  },
  {
    path: 'report',
    loadChildren: () => import('./modules/client/client.module').then((m) => m.ClientModule),
  },
  {
    path: 'admin',
    loadChildren: () => import('./modules/admin/admin.module').then((m) => m.AdminModule),
  },
  {
    path: '**',
    redirectTo: '',
    pathMatch: 'full'
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
