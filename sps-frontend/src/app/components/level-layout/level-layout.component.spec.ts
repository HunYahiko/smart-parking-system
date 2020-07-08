import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LevelLayoutComponent } from './level-layout.component';

describe('LevelLayoutComponent', () => {
  let component: LevelLayoutComponent;
  let fixture: ComponentFixture<LevelLayoutComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LevelLayoutComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LevelLayoutComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
