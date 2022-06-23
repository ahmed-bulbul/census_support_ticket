import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TestSubsComponent } from './test-subs.component';

describe('TestSubsComponent', () => {
  let component: TestSubsComponent;
  let fixture: ComponentFixture<TestSubsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TestSubsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TestSubsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
