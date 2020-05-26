import {AfterViewInit, Component, OnInit, QueryList, ViewChildren} from '@angular/core';
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

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit, AfterViewInit {

  @ViewChildren('parkingLots')
  parkingLots: QueryList<ParkingLotComponent>;

  levelLayout: LevelLayoutDto;
  layoutObjectsPositions: LayoutObject[][];

  constructor(private parkingLayoutService: ParkingLayoutService,
              private websocketService: WebsocketService,
              private router: Router) { }

  ngOnInit(): void {
    this.parkingLayoutService.getLevelLayout('2d4d2d88-45d1-4259-9cd8-91f8ae3dbba9').toPromise()
      .then(levelLayoutDto => {
        this.levelLayout = levelLayoutDto;
        console.log(this.levelLayout);
        this.layoutObjectsPositions = [];

        for (let i = 0; i < this.levelLayout.width; ++i) {
          this.layoutObjectsPositions[i] = [];
          for (let j = 0; j < this.levelLayout.length; ++j) {
            this.layoutObjectsPositions[i][j] = undefined;
          }
        }

        console.log('layoutObjectPositions length = ' + this.layoutObjectsPositions.length);

        for (const layoutObject of this.levelLayout.layoutObjects) {
          const row: number = layoutObject.x;
          const column: number = layoutObject.y;
          this.layoutObjectsPositions[row][column] = layoutObject;
        }

        console.log('layoutObjectPositions[2]: ' + JSON.stringify(this.layoutObjectsPositions[2]));
      })
      .catch(error => this.handleError(error));
  }

  ngAfterViewInit() {
    this.websocketService.getConnection().watch('/layout').subscribe((message) => {
      const layoutUpdateMessage: LayoutUpdateMessage = JSON.parse(message.body);
      const parkingLot = this.parkingLots.find( item => item.layoutObject.id === layoutUpdateMessage.layoutObjectId);
      parkingLot.updateStatus(layoutUpdateMessage);
    });
  }

  private handleError(httpError: HttpErrorResponse) {
    if (httpError.status === HttpStatus.FORBIDDEN) {
      this.router.navigateByUrl('/login')
        .then(() => alert('You are not authorized to check this pages!'))
        .catch(error => console.error('Something went wrong: ' + error));
    }

    if (httpError.status === HttpStatus.ERR_CONNECTION_REFUSED) {
      alert('Please check your back-end connection!');
    }

    if (httpError.status === HttpStatus.INTERNAL_SERVER_ERROR) {
      alert('Whoops! Stanislav did a booboo! Please contact him and tell him to get his shit together :)');
    }
  }

  get parkingLotTypeObject() { return LayoutObjectType.PARKING_LOT; }
  get roadTypeObject() { return LayoutObjectType.ROAD; }

  isParkingLot(layoutObject: LayoutObject): boolean {
    if (layoutObject === undefined) {
      return false;
    }
    return layoutObject.type === LayoutObjectType.PARKING_LOT;
  }

  isRoad(layoutObject: LayoutObject): boolean {
    if (layoutObject === undefined) {
      return false;
    }
    return layoutObject.type === LayoutObjectType.ROAD;
  }

}
