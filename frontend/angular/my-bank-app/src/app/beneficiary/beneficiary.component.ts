import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { BeneficiaryService } from '../services/beneficiary.service';
import { ReactiveFormsModule, FormBuilder, Validators, FormGroup } from '@angular/forms';
import { CommonModule, NgIf } from '@angular/common';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-beneficiary',
  standalone: true,
  imports: [RouterLink, FormsModule, CommonModule, ReactiveFormsModule],
  templateUrl: './beneficiary.component.html',
  styleUrls: ['./beneficiary.component.css'],
  providers: [BeneficiaryService]
})
export class BeneficiaryComponent implements OnInit {
  resetForm() {
    throw new Error('Method not implemented.');
  }
  beneficiaries: any[] = [];

  constructor(private beneficiaryService: BeneficiaryService, private fb: FormBuilder) {
  }
  ngOnInit() {
    this.loadBeneficiaries();
    console.log('Beneficiaries:', this.beneficiaries);
  }


  loadBeneficiaries() {
    console.log('Loading beneficiaries...');
    this.beneficiaryService.getBeneficiaries().subscribe(
      (data) => (this.beneficiaries = data),
      (error) => console.error('Error loading beneficiaries', error)
    );
  }

  editBeneficiary(b: any) {
    //    this.beneficiary = { ...b }; // Load data for editing
  }

  deleteBeneficiary(b: any) {
    if (confirm('Are you sure you want to delete this beneficiary?')) {
      this.beneficiaryService.deleteBeneficiary(b).subscribe(
        {
          next: x => { this.loadBeneficiaries() },
          // () => this.loadBeneficiaries(),
          error: er => console.error('Error deleting beneficiary', er)
        }
      );
    }
  }
}
