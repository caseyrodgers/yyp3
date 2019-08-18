import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { PosesListPage } from './poses-list';
const routes: Routes = [
  {
    path: '',
    component: PosesListPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PosesListPageRoutingModule {}
