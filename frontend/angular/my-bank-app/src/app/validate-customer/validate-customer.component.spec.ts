import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ValidateCustomerComponent } from './validate-customer.component';

describe('ValidateCustomerComponent', () => {
  let component: ValidateCustomerComponent;
  let fixture: ComponentFixture<ValidateCustomerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ValidateCustomerComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ValidateCustomerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
