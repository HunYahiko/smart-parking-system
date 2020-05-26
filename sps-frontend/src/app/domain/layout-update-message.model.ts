import {ParkingStatus} from './enums/parking-status.enum';

export interface LayoutUpdateMessage {
  parkingLotName: string;
  layoutObjectId: string;
  parkingStatus: ParkingStatus;
}
