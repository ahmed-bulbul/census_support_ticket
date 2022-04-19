import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MenuShowComponent } from './menu-show.component';

describe('MenuShowComponent', () => {
  let component: MenuShowComponent;
  let fixture: ComponentFixture<MenuShowComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MenuShowComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MenuShowComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
