import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AccountService, AccountDetailsDTO } from '../services/account.service';
import {
  Transaction,
  TransactionsService,
} from '../services/transactions.service';
import { CommonModule, NgClass } from '@angular/common';
import { BeneficiaryService } from '../services/beneficiary.service';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-transactions',
  standalone: true,
  imports: [NgClass, CommonModule, FormsModule],
  templateUrl: './transactions.component.html',
  styleUrls: ['./transactions.component.css'], // Fixed typo
})
export class TransactionsComponent implements OnInit {
  accountNumber: string = '';
  accountName: string = '';
  totalAmount: number | null = null;
  selectedBeneficiary: string = '';
  beneficiaries: any[] = [];
  transferAmount: number | null = null;
  accountDetails: AccountDetailsDTO[] = []; // Store account details
  errorMessage: string | null = null;
  selectedAccount: string = '';
  accountBalance: number = 0;
  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private accountService: AccountService,
    private transactionService: TransactionsService,
    private beneficiaryService: BeneficiaryService
  ) {}

  ngOnInit(): void {
    console.log('TransactionsComponent ngOnInit');
    this.loadBeneficiaries();
    this.fetchAccountDetails(); // Added fetchAccountDetails to load on init
  }

  getAccountName($event: any) {
    console.log('getAccountName', $event);
    this.accountDetails.filter((account) => {
      console.log('Account account.accountId:', account.accountId);
      if (account.accountNumber == $event) {
        console.log('Account account.balance:', account.balance);
        this.accountBalance = account.balance;
      }
    });

    //throw new Error('Method not implemented.');
  }

  // Load beneficiaries from BeneficiaryService
  loadBeneficiaries() {
    this.beneficiaryService.getBeneficiaries().subscribe(
      (data) => (this.beneficiaries = data),
      (error) => console.error('Error loading beneficiaries', error)
    );
  }

  // Fetch account details based on customer ID
  fetchAccountDetails(): void {
    const customerId = localStorage.getItem('customerId');
    console.log('Customer ID in transaction page:', customerId);

    if (customerId) {
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
    } else {
      this.errorMessage = 'Customer ID not found.';
    }
  }

  // Perform transfer logic
  transfer(): void {
    if (
      this.transferAmount &&
      this.selectedBeneficiary &&
      this.transferAmount > 0
    ) {
      console.log(`Transfer initiated:
        Account Number: ${this.selectedAccount},
        Transfer Amount: ${this.transferAmount},
        Beneficiary: ${this.selectedBeneficiary}
      `);
      alert('Transfer successful!');
    } else {
      alert('Please fill in all required fields.');
    }
  }
}
