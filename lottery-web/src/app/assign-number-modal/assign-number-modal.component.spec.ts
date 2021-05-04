import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AssignNumberModalComponent } from './assign-number-modal.component';

describe('AssignNumberModalComponent', () => {
  let component: AssignNumberModalComponent;
  let fixture: ComponentFixture<AssignNumberModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AssignNumberModalComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AssignNumberModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
