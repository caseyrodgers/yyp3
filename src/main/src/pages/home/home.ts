import { Component } from '@angular/core';
import { NavController } from 'ionic-angular';
import { YogaParser } from '../../services/yoga-parser/yoga-parser.service';

@Component({
  selector: 'page-home',
  templateUrl: 'home.html'
})
export class HomePage {
  yogaParser: YogaParser = new YogaParser();
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

  constructor(public navCtrl: NavController) {

  }

  parseClass() {
    let yogaClass = this.yogaParser.parseDef(this.yClassDef);
    alert('yoga class: ' + yogaClass);
  }

}
