import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { BeneficiaryService } from '../../services/beneficiary.service';
import { ReactiveFormsModule, FormBuilder, Validators, FormGroup } from '@angular/forms';
import { CommonModule, NgIf } from '@angular/common';
import { Router, RouterLink } from '@angular/router';

@Component({
  selector: 'app-add-beneficiary',
  standalone: true,
  imports: [FormsModule, CommonModule, ReactiveFormsModule,RouterLink],
  templateUrl: './add-beneficiary.component.html',
  styleUrl: './add-beneficiary.component.css'
})
export class AddBeneficiaryComponent implements OnInit {
  resetForm() {
    throw new Error('Method not implemented.');
  }
  beneficiaryForm: FormGroup;
  beneficiaries: any[] = [];
 beneficiary = this.resetBeneficiary();

  constructor(private beneficiaryService: BeneficiaryService, private fb: FormBuilder, private router: Router) {
    // Initialize the form
    this.beneficiaryForm = this.fb.group({
      beneficiaryName: ['', Validators.required],
      beneficiaryAccountNumber: ['', Validators.required],
      beneficiaryBankCode: ['', Validators.required],
      beneficiaryBankName: ['', Validators.required],
      beneficiaryEmail: ['', Validators.required],
      beneficiaryType: ['INTERNAL', Validators.required],
      relationship: ['', Validators.required],
      dailyTransferLimit: [null, Validators.required],
    });
  }

  ngOnInit() {
  }

  resetBeneficiary() {
    return {
      beneficiaryId: null,
      beneficiaryName: '',
      beneficiaryAccountNumber: '',
      beneficiaryBankCode: '',
      beneficiaryBankName: '',
      beneficiaryEmail: '',
      beneficiaryType: 'INTERNAL',
      relationship: '',
      dailyTransferLimit: null,
      customerId: localStorage.getItem('customerId')!,
    };
  }

  loadBeneficiaries() {
    console.log('Loading beneficiaries...');
    this.beneficiaryService.getBeneficiaries().subscribe(
      (data) => (this.beneficiaries = data),
      (error) => console.error('Error loading beneficiaries', error)
    );
  }

  onSubmit() {
    // console.log('Beneficiary:', this.beneficiaryForm.value);
    // this.beneficiary = { ...this.beneficiary, ...this.beneficiaryForm.value };
    
      if (this.beneficiary.beneficiaryId) {
        // Update existing beneficiary
        this.beneficiaryService.updateBeneficiary(this.beneficiary.beneficiaryId, this.beneficiary).subscribe(
          () => {
            this.loadBeneficiaries();
            this.beneficiary = this.resetBeneficiary();
          },
          (error) => console.error('Error updating beneficiary', error)
        );
      } else {
        // Add new beneficiary
        this.beneficiary = this.beneficiaryForm.value;
        this.beneficiary.customerId = localStorage.getItem('customerId')!;
        console.log('Beneficiary customerId id:', this.beneficiary.customerId);
        this.beneficiaryService.addBeneficiary(this.beneficiary).subscribe(
          () => {
            this.loadBeneficiaries();
            this.beneficiary = this.resetBeneficiary();
            this.router.navigate(['/app-beneficiary']);
          },
          (error) => console.error('Error adding beneficiary', error)
        );
      }
    
  }

}
