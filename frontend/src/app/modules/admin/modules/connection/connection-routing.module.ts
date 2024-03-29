import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ConnectionListComponent } from './components/connection-list/connection-list.component';

const routes: Routes = [
  {
    path:'',
    component: ConnectionListComponent,
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ConnectionRoutingModule { }
