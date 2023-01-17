import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SourcePathListComponent } from './components/source-path-list/source-path-list.component';

const routes: Routes = [
  {
    path:'',
    component: SourcePathListComponent,
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class SourcePathRoutingModule { }
