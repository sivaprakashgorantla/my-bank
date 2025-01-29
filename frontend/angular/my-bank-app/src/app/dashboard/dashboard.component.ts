import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { AccountService, AccountDetailsDTO } from '../services/account.service';
import { HttpHeaders } from '@angular/common/http';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css'],
})
export class DashboardComponent implements OnInit {
  showProfileMenu: boolean = false;
  username: string | null = '';
  accountDetails: AccountDetailsDTO[] = []; // Store account details
  errorMessage: string | null = null;

  constructor(private router: Router, private accountService: AccountService) {}

  ngOnInit(): void {
    // Fetch the logged-in user's username
    this.username = localStorage.getItem('username') || 'Guest';

    // Replace with dynamic user ID as needed
    const userId = localStorage.getItem('userId')!;
    const customerId = localStorage.getItem('customerId')!;

    console.log('ngOnInit fetchAccountDetails customerId ID:', customerId);
    if (customerId) {
      this.fetchAccountDetails(customerId);
    } else {
      this.errorMessage = 'Customer ID is missing.';
    }
  }

  toggleProfileMenu(): void {
    this.showProfileMenu = !this.showProfileMenu;
  }

  isActive(route: string): boolean {
    return this.router.url === route;
  }

  fetchAccountDetails(customerId: string): void {
    this.accountService.getAccountsByCustomerId(customerId).subscribe({
      next: (response) => {
        if (response.accounts) {
          this.accountDetails = response.accounts;
          this.errorMessage = null;
        } else {
          this.errorMessage = response.message;
        }
      },
      error: (err) => {
        this.errorMessage =
          'Failed to fetch account details. Please try again later.';
      },
    });

    this.accountService.getCustomerIdByUserId().subscribe({
      next: (response) => {
        console.log('customerId:', response);
        //localStorage.setItem('customerId', response.customerId);
      },
      error: (err) => {
        console.error('Failed to fetch customer ID:', err);
      },
    });
    
  }
}
