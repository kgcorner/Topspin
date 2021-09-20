import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OfferToupleComponent } from './offer-touple.component';

describe('OfferToupleComponent', () => {
  let component: OfferToupleComponent;
  let fixture: ComponentFixture<OfferToupleComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OfferToupleComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(OfferToupleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
