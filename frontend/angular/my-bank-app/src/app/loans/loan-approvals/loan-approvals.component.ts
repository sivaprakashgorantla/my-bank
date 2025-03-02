import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import { LoanSerciceService } from '../../services/loan-sercice.service';

interface LoanApplication {
  loanId: number;
  customerId: number;
  loanAmount: number;
  status: string;
  referenceNumber: string;
  termMonths: number;
}

@Component({
  selector: 'app-loan-approvals',
  standalone: true,
  imports: [CommonModule, RouterModule],

  templateUrl: './loan-approvals.component.html',
  styleUrls: ['./loan-approvals.component.css'],
})
export class LoanApprovalsComponent implements OnInit {
  loanApplications: LoanApplication[] = [];

  constructor(
    private loanService: LoanSerciceService,
    private router: Router
  ) {}
  ngOnInit(): void {
    this.loadLoanApplications();
  }

  private loadLoanApplications(): void {
    this.loanService.getApprovals().subscribe(
      (applications: LoanApplication[]) => {
        this.loanApplications = applications;
      },
      (error) => {
        console.error('Error loading loan applications', error);
      }
    );
  }

  private updateLoanStatus(loanId: number, newStatus: string) {
    //   const loan = this.loanApplications.find(app => app.loanId === loanId);
    //   if (loan) {
    //     loan.status = newStatus;
    //     this.loanService.updateLoanStatus(loanId, newStatus).subscribe(
    //       () => {
    //         console.log(`Loan ${loanId} status updated to ${newStatus}`);
    //       },
    //       (error) => {
    //         console.error(`Error updating loan ${loanId} status`, error);
    //       }
    //     );
    //   }
  }

  approveLoan(loanId: number) {
    this.updateLoanStatus(loanId, 'APPROVED');
  }

  rejectLoan(loanId: number) {
    this.updateLoanStatus(loanId, 'REJECTED');
  }

  viewDetails(referenceNumber: string) {
    console.log("viewDetails : " + referenceNumber);
    this.router.navigate(['/app-loan-details', referenceNumber]);  // ✅ Correct route
  }
  
}
