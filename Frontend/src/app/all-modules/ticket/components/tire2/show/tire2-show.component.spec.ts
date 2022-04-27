import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Tire2ShowComponent } from './tire2-show.component';

describe('Tire2ShowComponent', () => {
  let component: Tire2ShowComponent;
  let fixture: ComponentFixture<Tire2ShowComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ Tire2ShowComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(Tire2ShowComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
