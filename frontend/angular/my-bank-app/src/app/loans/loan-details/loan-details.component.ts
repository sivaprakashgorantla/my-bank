import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { LoanSerciceService } from '../../services/loan-sercice.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-loan-details',
  imports: [CommonModule,FormsModule],
  standalone: true,
  templateUrl: './loan-details.component.html',
  styleUrls: ['./loan-details.component.css'],
})
export class LoanDetailsComponent implements OnInit {
  loanDetails: any;
  remarkInput: string = ""; // For storing user input remark
  constructor(
    private route: ActivatedRoute,
    private loanService: LoanSerciceService,
    private router: Router
  ) {}

  ngOnInit(): void {
    const referenceNumber = this.route.snapshot.paramMap.get('referenceNumber');
    console.log("Reference Number:", referenceNumber);
    if (referenceNumber) {
      this.loanService.getLoanDetails(referenceNumber).subscribe((data) => {
        this.loanDetails = data;
      });
    }
  }
  goBack() {
    this.router.navigate(['../'], { relativeTo: this.route });
  }

  approveLoan() {
    if (!this.remarkInput.trim()) {
      alert("Please enter a remark before approving.");
      return;
    }
    this.loanDetails.status = "Approved";
    this.loanDetails.remark = this.remarkInput;
    console.log("Loan Approved:", this.loanDetails);
    this.loanService.approveLoan(this.loanDetails.loanId,this.remarkInput).subscribe(
      (response) => {
        console.log("Loan approved successfully", response);
        this.router.navigate(['../'], { relativeTo: this.route });
      },
      (error) => {
        console.error("Error approving loan", error);
      }
    );
  }
  rejectLoan() {
    if (!this.remarkInput.trim()) {
      alert("Please enter a remark before rejecting.");
      return;
    }
    this.loanDetails.status = "Rejected";
    this.loanDetails.remark = this.remarkInput;
    console.log("Loan Rejected:", this.loanDetails);
  }
}
