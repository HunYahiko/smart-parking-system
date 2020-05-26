import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {ParkingLotPositionDto} from '../domain/dto/parking-lot-position-dto.model';
import {ParkingLayoutDto} from '../domain/dto/parking-layout-dto.model';
import {LevelLayoutDto} from '../domain/dto/level-layout-dto.model';


@Injectable({
  providedIn: 'root'
})
export class ParkingLayoutService {
  private readonly BASE_URL = 'http://localhost:8080';
  private readonly LAYOUTS_RESOURCE = '/v1/api/layouts';

  constructor(private httpClient: HttpClient) { }

  public getLevelLayout(levelId: string): Observable<LevelLayoutDto> {
    return this.httpClient.get<LevelLayoutDto>(this.BASE_URL + this.LAYOUTS_RESOURCE +  '/' + levelId);
  }
}
