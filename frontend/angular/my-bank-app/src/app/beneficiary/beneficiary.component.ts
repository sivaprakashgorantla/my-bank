import { Component, OnInit } from '@angular/core';
import { BeneficiaryService } from '../services/beneficiary.service';
import { Router, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-beneficiary',
  standalone: true,
  imports: [RouterLink, CommonModule],  // Add this import
  templateUrl: './beneficiary.component.html',
  styleUrls: ['./beneficiary.component.css'],
  providers: [BeneficiaryService]
})
export class BeneficiaryComponent implements OnInit {
  beneficiaries: any[] = [];

  constructor(
    private beneficiaryService: BeneficiaryService,
    private router: Router
  ) { }

  ngOnInit() {
    this.loadBeneficiaries();
  }

  loadBeneficiaries() {
    this.beneficiaryService.getBeneficiaries().subscribe(
      (data) => (this.beneficiaries = data),
      (error) => console.error('Error loading beneficiaries', error)
    );
  }

  editBeneficiary(b: any) {
    // Navigate to edit page, passing the beneficiary ID
    console.log('Editing beneficiary:', b);
    if (b && b.beneficiaryId) {
      this.router.navigate(['/app-update-beneficiary', b.beneficiaryId]);
    } else {
      console.error('Beneficiary ID is missing');
    }
  }

  deleteBeneficiary(b: any) {
    if (confirm('Are you sure you want to delete this beneficiary?')) {
      this.beneficiaryService.deleteBeneficiary(b.beneficiaryId).subscribe(
        () => this.loadBeneficiaries(),
        (error) => console.error('Error deleting beneficiary', error)
      );
    }
  }
}
