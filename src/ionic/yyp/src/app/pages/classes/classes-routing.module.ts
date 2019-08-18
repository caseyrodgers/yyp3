import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { ClassesPage } from './classes';


const routes: Routes = [
  {
    path: '',
    component: ClassesPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ClassesPageRoutingModule { }
