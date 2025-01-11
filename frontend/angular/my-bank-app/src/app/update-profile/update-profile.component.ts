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
  roles = ['ADMIN', 'USER', 'MODERATOR'];

  constructor(private userService: UserService, private router:Router) { }

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
