import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Tire2ListComponent } from './tire2-list.component';

describe('Tire2ListComponent', () => {
  let component: Tire2ListComponent;
  let fixture: ComponentFixture<Tire2ListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ Tire2ListComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(Tire2ListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
