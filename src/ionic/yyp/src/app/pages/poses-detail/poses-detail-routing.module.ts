import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {PosesDetailPage} from './poses-detail';


const routes: Routes = [
  {
    path: '',
    component: PosesDetailPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PosesDetailPageRoutingModule { }
