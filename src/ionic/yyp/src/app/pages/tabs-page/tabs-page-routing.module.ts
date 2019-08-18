import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { TabsPage } from './tabs-page';
import {ClassesPage} from '../classes/classes';


const routes: Routes = [
  {
    path: 'tabs',
    component: TabsPage,
    children: [
      {
        path: 'classes',
        children: [
          {
            path: '',
            component: ClassesPage,
          },
          {
            path: 'session/:sessionId',
            loadChildren: () => import('../session-detail/session-detail.module').then(m => m.SessionDetailModule)
          }
        ]
      },
      {
        path: 'poses',
        children: [
          {
            path: '',
            loadChildren: () => import('../poses-list/poses-list.module').then(m => m.PosesListModule)
          },
          {
            path: 'poses/:sessionId',
            loadChildren: () => import('../poses-detail/poses-detail.module').then(m => m.PosesDetailModule)
          },
          {
            path: 'poses-details/:speakerId',
            loadChildren: () => import('../poses-detail/poses-detail.module').then(m => m.PosesDetailModule)
          }
        ]
      },
      {
        path: 'map',
        children: [
          {
            path: '',
            loadChildren: () => import('../map/map.module').then(m => m.MapModule)
          }
        ]
      },
      {
        path: 'about',
        children: [
          {
            path: '',
            loadChildren: () => import('../about/about.module').then(m => m.AboutModule)
          }
        ]
      },
      {
        path: '',
        redirectTo: '/app/tabs/schedule',
        pathMatch: 'full'
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class TabsPageRoutingModule { }

