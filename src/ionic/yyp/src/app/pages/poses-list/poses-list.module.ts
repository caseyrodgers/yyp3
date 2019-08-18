import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { IonicModule } from '@ionic/angular';

import { PosesListPage } from './poses-list';
import { PosesListPageRoutingModule } from './poses-list-routing.module';

@NgModule({
  imports: [
    CommonModule,
    IonicModule,
    PosesListPageRoutingModule
  ],
  declarations: [PosesListPage],
})
export class PosesListModule {}
