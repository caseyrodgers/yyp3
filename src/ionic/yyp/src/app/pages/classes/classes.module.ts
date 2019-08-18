import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { IonicModule } from '@ionic/angular';

import { ClassesPage } from './classes';
import {ClassesPageRoutingModule} from './classes-routing.module';
import {ClassesFilterPage} from '../classes-filter/classes-filter';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    ClassesPageRoutingModule
  ],
  declarations: [
    ClassesPage,
    ClassesFilterPage
  ],
  entryComponents: [
    ClassesFilterPage
  ]
})
export class ClassesModule { }
