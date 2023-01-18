import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PivotTableComponent } from './components/pivot-table/pivot-table.component';

const routes: Routes = [
  {
    path:'',
    component: PivotTableComponent,
  },
  {
    path:':id',
    component: PivotTableComponent,
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PivotRoutingModule { }
