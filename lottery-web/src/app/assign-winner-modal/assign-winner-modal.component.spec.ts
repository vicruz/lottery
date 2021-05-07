import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AssignWinnerModalComponent } from './assign-winner-modal.component';

describe('AssignWinnerModalComponent', () => {
  let component: AssignWinnerModalComponent;
  let fixture: ComponentFixture<AssignWinnerModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AssignWinnerModalComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AssignWinnerModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
