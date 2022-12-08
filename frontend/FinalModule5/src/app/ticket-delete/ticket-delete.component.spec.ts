import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TicketDeleteComponent } from './ticket-delete.component';

describe('TicketDeleteComponent', () => {
  let component: TicketDeleteComponent;
  let fixture: ComponentFixture<TicketDeleteComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TicketDeleteComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TicketDeleteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
