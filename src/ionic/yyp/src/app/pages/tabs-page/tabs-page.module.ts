import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { IonicModule } from '@ionic/angular';

import { TabsPage } from './tabs-page';
import { TabsPageRoutingModule } from './tabs-page-routing.module';
import { AboutModule } from '../about/about.module';
import { MapModule } from '../map/map.module';
import {ClassesModule} from '../classes/classes.module';
import {PosesListModule} from '../poses-list/poses-list.module';
import {PosesDetailModule} from '../poses-detail/poses-detail.module';

@NgModule({
  imports: [
    AboutModule,
    CommonModule,
    IonicModule,
    MapModule,
    ClassesModule,
    PosesListModule,
    PosesDetailModule,
    TabsPageRoutingModule
  ],
  declarations: [
    TabsPage,
  ]
})
export class TabsModule { }
