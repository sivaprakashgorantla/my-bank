import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { AccountService, AccountDetailsDTO } from '../services/account.service';
import { TransactionsComponent } from "../transactions/transactions.component";

@Component({
  selector: 'app-account-details',
  standalone: true,
  imports: [CommonModule, RouterModule, TransactionsComponent],
  templateUrl: './account-details.component.html',
  styleUrl: './account-details.component.css'
})
export class AccountDetailsComponent implements OnInit {
  accountNumber!: string;
username: string | null = '';
  accountDetails: AccountDetailsDTO[] = []; // Store account details
  errorMessage: string | null = null;

  constructor(private route: ActivatedRoute, private router: Router, private accountService: AccountService) {}

  ngOnInit(): void {
    // Retrieve the route parameter
    this.accountNumber = this.route.snapshot.paramMap.get('accountNumber') || '';
    console.log('Account Number:', this.accountNumber);
    this.username = localStorage.getItem('username') || 'Guest';

    // Replace with dynamic user ID as needed
    const customerId = localStorage.getItem('customerId') !;
    console.log('for fetchAccountDetails customerId ID:', customerId);
    if (customerId) {
      this.fetchAccountDetails(customerId);
    } else {
      this.errorMessage = 'Customer ID is missing.';
    }

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
        this.errorMessage = 'Failed to fetch account details. Please try again later.';
      },
    });
  }

}