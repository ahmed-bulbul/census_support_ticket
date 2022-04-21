import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Tire1ShowComponent } from './tire1-show.component';

describe('Tire1ShowComponent', () => {
  let component: Tire1ShowComponent;
  let fixture: ComponentFixture<Tire1ShowComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ Tire1ShowComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(Tire1ShowComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
