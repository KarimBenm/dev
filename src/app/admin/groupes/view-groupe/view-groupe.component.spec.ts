import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewGroupeComponent } from './view-groupe.component';

describe('ViewGroupeComponent', () => {
  let component: ViewGroupeComponent;
  let fixture: ComponentFixture<ViewGroupeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ViewGroupeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ViewGroupeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
