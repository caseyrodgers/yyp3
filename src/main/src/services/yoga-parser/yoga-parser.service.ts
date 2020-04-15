enum SectionType {
  SINGLE, DIRECTIVE, NUMBEROF
}

class LoopableSection {
  type: SectionType;
  raw = '';
}

export class YogaClass {
  constructor(sections: LoopableSection []) {
    this.sections = sections;
  }
  def: string;  // full definition
  sections: LoopableSection [];
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

  sections: LoopableSection [];
  classDef: string;

  dump() {
    console.log('YogaClass: ' + this.classDef);
    for (let i = 0; i < this.sections.length; i++) {
      const s = this.sections[i];
      console.log('YogaClass section#' + i + ':\n\n' + s.raw);
    }
  }

  /** Parse the class def by:
   *
   *  a. break into loopable sections
   *
   *  1. extract sections by separating by directives
   *  2. breaking into pieces separated by '-'
   *
   * @param def
   * @returns {{def: string}}
   */
  parseDef(classDef: string) {
    this.classDef = classDef;
    this.sections = this.extractLoopableSections(classDef);
    this.expandSections();
    return this;
  }


  /** for each section expand it depending on section type
   *
   */
  expandSections() {
    for (const section of this.sections) {
      const p = section.raw;
      if (p.startsWith('$')) {
        console.log('is directive', p);
        section.type = SectionType.DIRECTIVE;
      } else if(p.startsWith('#')) {
        console.log('is numberOf', p);
        section.type = SectionType.NUMBEROF;
      } else {
        section.type = SectionType.SINGLE;
        console.log('is single: ', p);
      }
    }
  }

  /** entire class is broken into sections that can be looped if needed.
   *  Each section is deliminated by:
   *
   *  $ ('directive')
   *  # ('count of')
   *
   * @param classDef
   */
  private extractLoopableSections(classDef: string): LoopableSection [] {
    const sections: LoopableSection [] = [];

    let currSection = null;
    for( const p of classDef) {
      if (p == '\n') {
        continue;  // skip newlines
      }

      if (p === '$' || p == '#' || currSection == null) {
        currSection = new LoopableSection();
        sections.push(currSection);
      }

      currSection.raw += p;

      if (p === ')') {
        currSection = null;  // end of current section
      }
    }
    return sections;
  }

  private createNumberOf(p: string) {
    let numOf = Number(p.charAt(1));
    const startOf = p.indexOf('(');
    const endOf = p.indexOf(')');
    const raw = p.substr(startOf, endOf);
    let realP = "";
    while (numOf-- > 0) {
      if (realP.length > 0) {
        realP += ', ';
      }
      realP += raw;
    }
    return realP;
  }
}
