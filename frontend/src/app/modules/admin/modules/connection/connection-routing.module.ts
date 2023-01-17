import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ApplicationListComponent } from '../application/components/application-list/application-list.component';

const routes: Routes = [
  {
    path:'',
    component: ApplicationListComponent,
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ConnectionRoutingModule { }
