import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { AuthService } from '../auth.service';
import { CommonModule, NgIf } from '@angular/common';
import { DashboardComponent } from "../dashboard/dashboard.component";
import { BeneficiaryListComponent } from "../beneficiary-list/beneficiary-list.component";
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-user-otp-validation',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule,NgIf,RouterLink],
  templateUrl: './user-otp-validation.component.html',
  styleUrls: ['./user-otp-validation.component.css']
})
export class UserOtpValidationComponent {
  otpForm: FormGroup;
  isSuccess = false;
  constructor(private fb: FormBuilder, private authService: AuthService) {
    this.otpForm = this.fb.group({
      otp: ['', [Validators.required, Validators.pattern(/^\d{6}$/)]], // Only 6-digit numbers allowed
    });
  }

  onSubmitOtp() {
    if (this.otpForm.valid) {
      const { otp } = this.otpForm.value;
      console.log('Submitting OTP:', otp);
      
      this.authService
        .validateOtp(otp)
        .subscribe(
          (response) => {
            alert('OTP validated successfully!');
            console.log(response);
            this.isSuccess = true;
            localStorage.removeItem('phoneNumber');
            localStorage.removeItem('id');
          },
          (error) => {
            alert('OTP validation failed.');
            console.error('Error:', error);
          }
        );
    }
  }
}
