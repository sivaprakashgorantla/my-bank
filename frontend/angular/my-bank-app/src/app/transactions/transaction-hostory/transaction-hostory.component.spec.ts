import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TransactionHostoryComponent } from './transaction-hostory.component';

describe('TransactionHostoryComponent', () => {
  let component: TransactionHostoryComponent;
  let fixture: ComponentFixture<TransactionHostoryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TransactionHostoryComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TransactionHostoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
