import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TabletListComponent } from './tablet-list.component';

describe('TabletListComponent', () => {
  let component: TabletListComponent;
  let fixture: ComponentFixture<TabletListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TabletListComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TabletListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
