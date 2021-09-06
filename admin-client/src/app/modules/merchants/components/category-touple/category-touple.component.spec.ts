import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CategoryToupleComponent } from './category-touple.component';

describe('CategoryToupleComponent', () => {
  let component: CategoryToupleComponent;
  let fixture: ComponentFixture<CategoryToupleComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CategoryToupleComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CategoryToupleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
