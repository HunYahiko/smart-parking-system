import {ChangeDetectorRef, Component, Input, OnInit} from '@angular/core';
import {LayoutObjectOrientation} from '../../domain/enums/layout-object-orientation.enum';
import {LayoutObject} from '../../domain/dto/layout-object.model';
import {LayoutUpdateMessage} from '../../domain/layout-update-message.model';
import {ParkingStatus} from '../../domain/enums/parking-status.enum';

@Component({
  selector: 'app-parking-lot',
  templateUrl: './parking-lot.component.html',
  styleUrls: ['./parking-lot.component.scss']
})
export class ParkingLotComponent implements OnInit {

  @Input() layoutObject: LayoutObject;

  parkingLotName: string = null;
  parkingLotStatus: ParkingStatus = null;

  constructor() { }

  ngOnInit(): void {
  }

  get up() { return this.layoutObject.orientation === LayoutObjectOrientation.UP; }
  get down() { return this.layoutObject.orientation === LayoutObjectOrientation.DOWN; }
  get left() { return this.layoutObject.orientation === LayoutObjectOrientation.LEFT; }
  get right() { return this.layoutObject.orientation === LayoutObjectOrientation.RIGHT; }
  get upright() { return this.layoutObject.orientation === LayoutObjectOrientation.UPRIGHT; }
  get downright() { return this.layoutObject.orientation === LayoutObjectOrientation.DOWNRIGHT; }
  get upleft() { return this.layoutObject.orientation === LayoutObjectOrientation.UPLEFT; }
  get downleft() { return this.layoutObject.orientation === LayoutObjectOrientation.DOWNLEFT; }

  get unresponsive() { return this.parkingLotStatus === ParkingStatus.UNRESPONSIVE; }
  get booked() { return this.parkingLotStatus === ParkingStatus.BOOKED; }
  get free() { return this.parkingLotStatus === ParkingStatus.FREE; }
  get occupied() { return this.parkingLotStatus === ParkingStatus.OCCUPIED; }

  get objectId() { return this.layoutObject.id; }

  updateStatus(layoutUpdateMessage: LayoutUpdateMessage) {
    if (this.parkingLotName == null) {
      this.parkingLotName = layoutUpdateMessage.parkingLotName;
    }
    this.parkingLotStatus = layoutUpdateMessage.parkingStatus;
    console.log(this.parkingLotStatus);
  }

}
