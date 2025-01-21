import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserOtpValidationComponent } from './user-otp-validation.component';

describe('UserOtpValidationComponent', () => {
  let component: UserOtpValidationComponent;
  let fixture: ComponentFixture<UserOtpValidationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [UserOtpValidationComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UserOtpValidationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
