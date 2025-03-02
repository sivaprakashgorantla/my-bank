import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { Observable } from 'rxjs';
import { LoanSerciceService } from '../../services/loan-sercice.service';

@Component({
  selector: 'app-loan-application',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule],
  templateUrl: './loan-application.component.html',
  styleUrls: ['./loan-application.component.css'],
})
export class LoanApplicationComponent implements OnInit {
  loanAmount!: number;
  loanPurpose!: string;
  termMonths!: number;
  referenceNumber: string | null = null;
  errorMessage: string | null = null;
  loanTypes: string[] = [];
  loanType!: string;
  interestRate!: number;

  constructor(private loanService: LoanSerciceService) {}
  ngOnInit(): void {
    this.getLoanType().subscribe({
      next: (loanTypes) => {
        this.loanTypes = loanTypes;
      },
      error: (error) => {
        this.errorMessage = 'Failed to load loan types. Please try again.';
        console.error('Error loading loan types:', error);
      },
    });
  }

  submitLoanApplication() {
    this.loanService
      .applyForLoan(this.loanAmount, this.loanPurpose, this.termMonths)
      .subscribe({
        next: (response) => {
          this.referenceNumber =
            response.referenceNumber ||
            'REF' + Math.floor(Math.random() * 1000000);
          this.errorMessage = null;
          this.resetForm();
        },
        error: (error) => {
          this.errorMessage = 'Loan application failed. Please try again.';
          console.error('Loan application error:', error);
        },
      });
  }

  private resetForm() {
    this.loanAmount = 0;
    this.loanPurpose = '';
    this.termMonths = 0;
  }
  getLoanType(): Observable<any> {
    return this.loanService.getLoanTypes();
  }
  setLoanType(selectedType: any) {
    console.log('selectedType:', selectedType.target.value);
     this.loanType = selectedType.target.value;
     if (this.loanType) {
       this.loanService.getInterestRate(this.loanType).subscribe({
         next: (rate) => {
           this.interestRate = rate;
         },
         error: (error) => {
           console.error('Failed to fetch interest rate:', error);
         },
       });
     }
  }
}
