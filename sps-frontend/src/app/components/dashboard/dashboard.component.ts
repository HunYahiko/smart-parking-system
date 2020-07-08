import {AfterViewInit, Component, OnInit, QueryList, ViewChild, ViewChildren} from '@angular/core';
import {ParkingLayoutService} from '../../service/parking-layout.service';
import {ParkingLotPositionDto} from '../../domain/dto/parking-lot-position-dto.model';
import {ParkingLayoutDto} from '../../domain/dto/parking-layout-dto.model';
import {LevelLayoutDto} from '../../domain/dto/level-layout-dto.model';
import {LayoutObject} from '../../domain/dto/layout-object.model';
import {LayoutObjectType} from '../../domain/enums/layout-object-type.enum';
import {ParkingLotComponent} from '../parking-lot/parking-lot.component';
import {RxStompService} from '@stomp/ng2-stompjs';
import {WebsocketService} from '../../service/websocket.service';
import {LayoutUpdateMessage} from '../../domain/layout-update-message.model';
import {HttpErrorResponse} from '@angular/common/http';
import {HttpStatus} from '../../enums/HttpStatus';
import {Router} from '@angular/router';
import {LevelLayoutComponent} from '../level-layout/level-layout.component';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {

  @ViewChild('levelLayout')
  levelLayoutComponent: LevelLayoutComponent;

  levelLayout: LevelLayoutDto;

  constructor() { }

  ngOnInit(): void {
  }

  get parkingLotTypeObject() { return LayoutObjectType.PARKING_LOT; }
  get roadTypeObject() { return LayoutObjectType.ROAD; }

}
