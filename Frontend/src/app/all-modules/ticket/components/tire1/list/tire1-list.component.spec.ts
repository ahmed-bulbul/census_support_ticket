import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Tire1ListComponent } from './tire1-list.component';

describe('Tire1ListComponent', () => {
  let component: Tire1ListComponent;
  let fixture: ComponentFixture<Tire1ListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ Tire1ListComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(Tire1ListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
