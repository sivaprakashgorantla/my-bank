import { Component, OnInit } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { User } from '../user';
import { UserService } from '../user.service';
import { MatSelectModule } from '@angular/material/select';
import { CommonModule } from '@angular/common';
import { MatFormField, MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { Router } from '@angular/router';
@Component({
  selector: 'app-update-profile',
  standalone: true,
  imports: [MatSelectModule, CommonModule, FormsModule, MatFormFieldModule, MatInputModule,MatDatepickerModule, MatNativeDateModule],
  templateUrl: './update-profile.component.html',
  styleUrls: ['./update-profile.component.css'],
})
export class UpdateProfileComponent implements OnInit {
  user: User = {
    userId: 0,
    username: '',
    password: '',
    email: '',
    phoneNumber: '',
    firstName: '',
    lastName: '',
    dateOfBirth: null,
    address: '',
    otp: '',
    customerId: '',
    createdAt: null,
    updatedAt: null,
    profileUpdated: 'N',
    status: 'ACTIVE',
    role: 'USER',
  };

  statuses = ['ACTIVE', 'INACTIVE', 'SUSPENDED'];
  otpSent: boolean = false;  // Flag to check if OTP is sent
  otp: string = '';  // For storing OTP input
  roles = ['ADMIN', 'USER', 'MODERATOR'];

  constructor(private userService: UserService, private router: Router) {}

  ngOnInit(): void {
    this.fetchUserProfile();
  }

  fetchUserProfile() {
    this.userService.getUserProfile().subscribe(
      (data: User) => {
        console.log('User profile:', data);
        this.user = data;
      },
      (error) => {
        console.error('Error fetching user profile:', error);
      }
    );
  }

  // Send OTP logic
  sendOtp() {
    console.log('Sending OTP for user:', this.user.userId);
    this.userService.sendOtp(this.user.userId).subscribe(
      (response: any) => {
        console.log('OTP sent successfully:', response);
        this.otpSent = true; // Make fields read-only
        alert('OTP sent successfully!'+response.otp);
        this.user.otp = response.otp; // Set the OTP value
      },
      (error: any) => {
        console.error('Error sending OTP:', error);
        alert('Error sending OTP!');
      }
    );
  }
  // OTP Validation logic
  validateOtp() {
    console.log('Validating OTP:', this.otp);
    this.userService.validateOtp(this.user.userId, this.otp).subscribe(
      (response: any) => {
        console.log('OTP validated successfully:', response);
        alert('OTP validated successfully!');
        //this.router.navigate(['/dashboard']);
      },
      (error: any) => {
        console.error('Error validating OTP:', error);
        alert('Error validating OTP!');
      }
    );
  }

  onSubmit() {
    console.log('update User submit:', this.user);
    this.userService.updateUserProfile(this.user).subscribe(
      (response: any) => {
        console.log('Profile updated successfully:', response);
        alert('Profile updated successfully!');
        this.router.navigate(['/dashboard']);
      },
      (error: any) => {
        console.error('Error updating profile:', error);
        alert('Error updating profile!');
      }
    );
  }
}
