import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { BeneficiaryService } from '../../services/beneficiary.service';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-update-beneficiary',
  standalone: true,
  imports: [FormsModule, CommonModule, ReactiveFormsModule],
  templateUrl: './update-beneficiary.component.html',
  styleUrls: ['./update-beneficiary.component.css']
})
export class UpdateBeneficiaryComponent implements OnInit {
  beneficiaryForm: FormGroup;
  beneficiaryId!: number;
  beneficiaries: any[] = [];

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private beneficiaryService: BeneficiaryService,
    private fb: FormBuilder
  ) {
    this.beneficiaryForm = this.fb.group({
      beneficiaryName: [{ value: '', disabled: true }],
      beneficiaryAccountNumber: [{ value: '', disabled: true }],
      beneficiaryBankName: [{ value: '', disabled: true }],
      beneficiaryEmail: ['', [Validators.required, Validators.email]],
      beneficiaryType: [{ value: '', disabled: true }]
    });
  }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.beneficiaryId = params['beneficiaryId']; // Get the ID from the route
      this.loadBeneficiaryData();
    });
  }

  // loadBeneficiaryData() {
  //   this.beneficiaryService.getBeneficiaries().subscribe(
  //     (data) => {
  //       console.log('Beneficiaries all:', data);
  //       const beneficiary = data.find(b => b.beneficiaryId === this.beneficiaryId);
  //       console.log('Beneficiary from database :', beneficiary);
  //       if (beneficiary) {

  //         this.beneficiaryForm.patchValue(beneficiary); // Pre-fill the form with beneficiary data
  //       }
  //     },
  //     (error) => console.error('Error loading beneficiary details', error)
  //   );
  // }
  loadBeneficiaryData() {
    this.beneficiaryService.getBeneficiaries().subscribe(
      (data) => {
        console.log('Beneficiaries:', data);  // Check the structure of the response
        const beneficiary = data.find(b => Number(b.beneficiaryId) === Number(this.beneficiaryId));
        
        if (beneficiary) {
          console.log('Found beneficiary:', beneficiary); // Log the found beneficiary
          this.beneficiaryForm.patchValue(beneficiary); // Pre-fill the form with beneficiary data
        } else {
          console.error('Beneficiary not found for ID:', this.beneficiaryId);  // Handle if no match found
        }
      },
      (error) => console.error('Error loading beneficiary details', error)
    );
  }
  
  

  onSubmit() {
    if (this.beneficiaryForm.valid) {
      this.beneficiaryService.updateBeneficiary(this.beneficiaryId, this.beneficiaryForm.value).subscribe(
        () => this.router.navigate(['/app-beneficiary']),
        (error) => console.error('Error updating beneficiary', error)
      );
    }
  }
}
