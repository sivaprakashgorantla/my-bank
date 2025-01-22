import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { UserService } from '../user.service';
import { CommonModule } from '@angular/common';
import { FormsModule, NgModel } from '@angular/forms';

@Component({
  selector: 'app-validate-customer',
  standalone: true,
  imports: [RouterLink, CommonModule, FormsModule],
  templateUrl: './validate-customer.component.html',
  styleUrl: './validate-customer.component.css',
})
export class ValidateCustomerComponent {
  username: string = '';
  email: string = '';
  phoneNumber: string = '';
  firstName: string = '';
  lastName: string = '';
  dateOfBirth: string = '';
  address: string = '';
  customerId: string = '';
  status: string = '';
  role: string = '';
  profileUpdated: string = '';
  constructor(private userService: UserService) {}

  ngOnInit(): void {
    this.fetchUserProfile();
  }

  fetchUserProfile() {
    console.log('Fetching user profile...');
    this.userService.getUserProfile().subscribe(
      (data) => {
        this.username = data.username;
        this.email = data.email;
        this.phoneNumber = data.phoneNumber;
        this.firstName = data.firstName;
        this.lastName = data.lastName;
        this.dateOfBirth = data.dateOfBirth;
        this.address = data.address;
        this.customerId = data.customerId;
        this.status = data.status;
        this.role = data.role;
        this.profileUpdated = data.profileUpdated;
      },
      (error) => {
        console.error('Error fetching user profile:', error);
      }
    );
  }

  // validateCustomer() {
  //   console.log('Validating customer...');
  //   this.userService.validateCustomer().subscribe(
  //     (data) => {
  //       this.username = data.username;
  //       this.email = data.email;
  //       this.phoneNumber = data.phoneNumber;
  //       this.firstName = data.firstName;
  //       this.lastName = data.lastName;
  //       this.dateOfBirth = data.dateOfBirth;
  //       this.address = data.address;
  //       this.customerId = data.customerId;
  //       this.status = data.status;
  //       this.role = data.role;
  //       this.profileUpdated = data.profileUpdated;
  //     },
  //     (error) => {
  //       console.error('Error validating customer:', error);
  //     }
  //   );
  // }
}
