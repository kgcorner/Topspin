import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MerchantStatsComponent } from './merchant-stats.component';

describe('MerchantStatsComponent', () => {
  let component: MerchantStatsComponent;
  let fixture: ComponentFixture<MerchantStatsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MerchantStatsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MerchantStatsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
