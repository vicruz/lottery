import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RaffleEditComponent } from './raffle-edit.component';

describe('RaffleEditComponent', () => {
  let component: RaffleEditComponent;
  let fixture: ComponentFixture<RaffleEditComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RaffleEditComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RaffleEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
