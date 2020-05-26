import { TestBed } from '@angular/core/testing';

import { ParkingLayoutService } from './parking-layout.service';

describe('ParkingLayoutService', () => {
  let service: ParkingLayoutService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ParkingLayoutService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
