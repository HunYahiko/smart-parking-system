import {ParkingLotPositionDto} from './parking-lot-position-dto.model';

export interface ParkingLayoutDto {
  parkingLotsPositions: Array<ParkingLotPositionDto>;
  rows: number;
  columns: number;
}
