import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { IonicModule } from '@ionic/angular';
import {PosesDetailPageRoutingModule} from './poses-detail-routing.module';
import {PosesDetailPage} from './poses-detail';

@NgModule({
  imports: [
    CommonModule,
    IonicModule,
    PosesDetailPageRoutingModule
  ],
  declarations: [
    PosesDetailPage,
  ]
})
export class PosesDetailModule { }
