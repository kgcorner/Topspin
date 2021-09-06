import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MerchantToupleComponent } from './merchant-touple.component';

describe('MerchantToupleComponent', () => {
  let component: MerchantToupleComponent;
  let fixture: ComponentFixture<MerchantToupleComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MerchantToupleComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MerchantToupleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
