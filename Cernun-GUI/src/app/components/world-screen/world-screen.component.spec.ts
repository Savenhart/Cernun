import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { WorldScreenComponent } from './world-screen.component';

describe('WorldScreenComponent', () => {
  let component: WorldScreenComponent;
  let fixture: ComponentFixture<WorldScreenComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ WorldScreenComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(WorldScreenComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
