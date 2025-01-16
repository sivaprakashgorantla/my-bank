import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { RouterModule } from '@angular/router';
import { AccountService, AccountDetailsDTO,  } from '../services/account.service';
import { Transaction, TransactionsService } from '../services/transactions.service';
import { CommonModule, NgClass } from '@angular/common';

@Component({
  selector: 'app-transaction-history',
  standalone: true,
  imports: [NgClass,CommonModule],
  templateUrl: './transaction-history.component.html',
  styleUrl: './transaction-history.component.css'
})
export class TransactionHistoryComponent  implements OnInit {
  accountNumber!: string;
  transactions: Transaction[] = [];
  errorMessage: string | null = null;

constructor(private route: ActivatedRoute, private router: Router, private accountService: AccountService, private transactionService: TransactionsService) {}

  ngOnInit(): void {
    console.log('TransactionsComponent ngOnInit');
    this.loadRecentTransactions();
  }

  loadRecentTransactions(): void {
    this.accountNumber = this.route.snapshot.paramMap.get('accountNumber') || '';
    console.log('TransactionsComponent Account Number:', this.accountNumber);
    
    this.transactionService.getAccountsByAccountNumber(this.accountNumber).subscribe({
      next: (data) => (this.transactions = data.transactions),
      error: (err) => (this.errorMessage = 'Failed to load transactions')
    });
  }
}
