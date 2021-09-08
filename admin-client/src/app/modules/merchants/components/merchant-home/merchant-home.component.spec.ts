import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MerchantHomeComponent } from './merchant-home.component';

describe('MerchantHomeComponent', () => {
  let component: MerchantHomeComponent;
  let fixture: ComponentFixture<MerchantHomeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MerchantHomeComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MerchantHomeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
