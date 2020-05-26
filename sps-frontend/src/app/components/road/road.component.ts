import {Component, Input, OnInit} from '@angular/core';
import {LayoutObjectOrientation} from '../../domain/enums/layout-object-orientation.enum';

@Component({
  selector: 'app-road',
  templateUrl: './road.component.html',
  styleUrls: ['./road.component.scss']
})
export class RoadComponent implements OnInit {
  @Input() orientation: LayoutObjectOrientation;

  indicationArrow: string;

  constructor() { }

  ngOnInit(): void {
    this.setIndicationArrow();
  }

  setIndicationArrow() {
    switch (this.orientation) {
      case LayoutObjectOrientation.UP:
        this.indicationArrow = 'arrow_upward';
        break;

      case LayoutObjectOrientation.DOWN:
        this.indicationArrow = 'arrow_downward';
        break;

      case LayoutObjectOrientation.LEFT:
        this.indicationArrow = 'arrow_back';
        break;

      case LayoutObjectOrientation.RIGHT:
        this.indicationArrow = 'arrow_forward';
        break;

      default:
        this.indicationArrow = '';
        break;
    }
  }

  get up() { return this.orientation === LayoutObjectOrientation.UP; }
  get down() { return this.orientation === LayoutObjectOrientation.DOWN; }
  get left() { return this.orientation === LayoutObjectOrientation.LEFT; }
  get right() { return this.orientation === LayoutObjectOrientation.RIGHT; }
  get upright() { return this.orientation === LayoutObjectOrientation.UPRIGHT; }
  get downright() { return this.orientation === LayoutObjectOrientation.DOWNRIGHT; }
  get upleft() { return this.orientation === LayoutObjectOrientation.UPLEFT; }
  get downleft() { return this.orientation === LayoutObjectOrientation.DOWNLEFT; }

}
