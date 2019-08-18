/**
 * Created by casey on 7/4/2017.
 */

export interface YogaClass {
  def: string;
}

/** YogaSection is a section of the class
 *  that moves from one pose to the next
 *
 *  A section is a defined bountry between different
 *  flows of the class.
 *
 *  1. straight sequence:  ab - cd - ef ... etc..
 *  2. directive, which can be either:
 *       Repeat: #5(ab, cd, ef)  -- repeat sequence n times
 *       Right/Left:  $RL(ab, cd, ef) -- do sequence on left, then repeat on right
 *  3. template: $T(TEMPLATE_NAME)  -- extracts and inserts named template
 *
 */
export interface YogaPose {
  key: string;
  name: string;
}

export interface YogaSection {
  sequence: YogaPose []
}

export interface LeftRightSection extends YogaSection {

}

export interface RepeatSection extends YogaSection {

}


export class YogaParser {


  /** Parse the class def by:
   *
   *  1. extract sections by separating by directives
   *  2. breaking into pieces separated by '-'
   *
   * @param def
   * @returns {{def: string}}
   */
  parseDef(def: string): YogaClass {
    let yogaClass = {def: def};




    return yogaClass;
  }
}
