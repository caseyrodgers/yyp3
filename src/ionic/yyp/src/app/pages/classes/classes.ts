import { Component, ViewChild, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AlertController, IonList, LoadingController, ModalController, ToastController } from '@ionic/angular';

import { ScheduleFilterPage } from '../schedule-filter/schedule-filter';
import { ConferenceData } from '../../providers/conference-data';
import { UserData } from '../../providers/user-data';
import {ClassesService} from '../../providers/classes.service';
import {YogaParser} from "../../../../../../main/src/services/yoga-parser/yoga-parser.service";
import {YogaClassPlayer} from "../../../../../../main/src/services/yoga-parser/yoga-class-player";

@Component({
  selector: 'page-classes',
  templateUrl: 'classes.html',
  styleUrls: ['./classes.scss'],
})


export class ClassesPage implements OnInit {
  // Gets a reference to the list element
  @ViewChild('classesList', { static: true }) classesList: IonList;

  dayIndex = 0;
  queryText = '';
  segment = 'all';
  excludeTracks: any = [];
  shownSessions: any = [];
  groups: any = [];
  confDate: string;

  yClassDef: string =
    `
s:45 - he:30  - dd:15 - 
#3(sus:30) - 
std - ff:20 - cb:20:1 -  sutd:30 -  br:30 - ub:20:1 - hb:30 - bu:30 - wsf:30 - rbu:30 - 
$RL(rkc:30:1, rlce:60:1, eofn:30, rt:30, st:30) - rbu:30:1 - 
#3(b:20,tll:15) - 
$RL(pl , sp:20 ,pkc:20,1ld:20:1,hl:20:1, ll:20, l:20)  -  std - 
$RL(skc:20, hl:20, w2:20:1, w1:20:1, w3:20:1,  w2:20,  rw:20, pyr:20, w2:20, esa:20, hm:20, te:20, ea:20)- 
std - ho:20- hoff:20 - cr:20 - pl -  
$RL(pi:20:1,c:15) -  
sutd:15  - 
#10(tll:15) - 
$T(sv)
    `;

  constructor(
    public alertCtrl: AlertController,
    public confData: ConferenceData,
    public loadingCtrl: LoadingController,
    public modalCtrl: ModalController,
    public router: Router,
    public toastCtrl: ToastController,
    public user: UserData,
    private classesService: ClassesService
  ) {
  }

  ngOnInit() {
    this.updateSchedule();
    this.readClasses();

    this.runTests();
  }

  runTests() {
    const yogaClass = new YogaParser().parseDef(this.yClassDef);
    new YogaClassPlayer().playClass(yogaClass);
  }

  readClasses() {
    this.classesService.getAllClasses()
      .then(classes => {
        alert('classes: ' + classes);
      }, (err) => {
        console.log(err);
      });
  }

  updateSchedule() {
    // Close any open sliding items when the schedule updates
    if (this.classesList) {
      this.classesList.closeSlidingItems();
    }

    this.confData.getTimeline(this.dayIndex, this.queryText, this.excludeTracks, this.segment).subscribe((data: any) => {
      this.shownSessions = data.shownSessions;
      this.groups = data.groups;
    });
  }

  async presentFilter() {
    const modal = await this.modalCtrl.create({
      component: ScheduleFilterPage,
      componentProps: { excludedTracks: this.excludeTracks }
    });
    await modal.present();

    const { data } = await modal.onWillDismiss();
    if (data) {
      this.excludeTracks = data;
      this.updateSchedule();
    }
  }

  async addFavorite(slidingItem: HTMLIonItemSlidingElement, sessionData: any) {
    if (this.user.hasFavorite(sessionData.name)) {
      // woops, they already favorited it! What shall we do!?
      // prompt them to remove it
      this.removeFavorite(slidingItem, sessionData, 'Favorite already added');
    } else {
      // remember this session as a user favorite
      this.user.addFavorite(sessionData.name);

      // create an alert instance
      const alert = await this.alertCtrl.create({
        header: 'Favorite Added',
        buttons: [{
          text: 'OK',
          handler: () => {
            // close the sliding item
            slidingItem.close();
          }
        }]
      });
      // now present the alert on top of all other content
      await alert.present();
    }

  }

  async removeFavorite(slidingItem: HTMLIonItemSlidingElement, sessionData: any, title: string) {
    const alert = await this.alertCtrl.create({
      header: title,
      message: 'Would you like to remove this session from your favorites?',
      buttons: [
        {
          text: 'Cancel',
          handler: () => {
            // they clicked the cancel button, do not remove the session
            // close the sliding item and hide the option buttons
            slidingItem.close();
          }
        },
        {
          text: 'Remove',
          handler: () => {
            // they want to remove this session from their favorites
            this.user.removeFavorite(sessionData.name);
            this.updateSchedule();

            // close the sliding item and hide the option buttons
            slidingItem.close();
          }
        }
      ]
    });
    // now present the alert on top of all other content
    await alert.present();
  }

  async openSocial(network: string, fab: HTMLIonFabElement) {
    const loading = await this.loadingCtrl.create({
      message: `Posting to ${network}`,
      duration: (Math.random() * 1000) + 500
    });
    await loading.present();
    await loading.onWillDismiss();
    fab.close();
  }

  createHardClass() {
    alert('hard');
  }

  createEasyClass() {
    alert('easy');
  }

  parasePose() {

    new YogaParser().parseDef(this.yClassDef);
  }

}
