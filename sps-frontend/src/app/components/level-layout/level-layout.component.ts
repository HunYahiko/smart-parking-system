import {AfterViewInit, Component, Input, OnInit, QueryList, ViewChildren} from '@angular/core';
import {ParkingLotComponent} from '../parking-lot/parking-lot.component';
import {ParkingLayoutService} from '../../service/parking-layout.service';
import {WebsocketService} from '../../service/websocket.service';
import {Router} from '@angular/router';
import {LevelLayoutDto} from '../../domain/dto/level-layout-dto.model';
import {LayoutObject} from '../../domain/dto/layout-object.model';
import {HttpErrorResponse} from '@angular/common/http';
import {HttpStatus} from '../../enums/HttpStatus';
import {LayoutUpdateMessage} from '../../domain/layout-update-message.model';
import {LayoutObjectType} from '../../domain/enums/layout-object-type.enum';
import {LevelNameListingDto} from '../../domain/dto/level-name-listing-dto.model';
import {MatOptionSelectionChange} from '@angular/material/core';

@Component({
  selector: 'app-level-layout',
  templateUrl: './level-layout.component.html',
  styleUrls: ['./level-layout.component.scss']
})
export class LevelLayoutComponent implements OnInit, AfterViewInit {

  @ViewChildren('parkingLots')
  parkingLots: QueryList<ParkingLotComponent>;

  levelLayout: LevelLayoutDto;
  layoutObjectsPositions: LayoutObject[][];
  levelNameListing: LevelNameListingDto[];

  constructor(private parkingLayoutService: ParkingLayoutService,
              private websocketService: WebsocketService,
              private router: Router) { }

  ngOnInit(): void {
    this.parkingLayoutService.getLevelNameListing('a97a5887-72a1-44f4-9e3d-4ae495d1db5d').toPromise()
      .then(value => this.levelNameListing = value)
      .catch(error => this.handleError(error));
  }

  public updateLayout(event: MatOptionSelectionChange): void {
    this.parkingLayoutService.getLevelLayout(event.source.value).toPromise()
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
      if (parkingLot) { parkingLot.updateStatus(layoutUpdateMessage); }
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
