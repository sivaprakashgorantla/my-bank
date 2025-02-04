import { CommonModule, NgIf } from '@angular/common';
import { Component } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';

interface Person {
  name: string;
  age: number;
  email: string;
  phone: string;
}

@Component({
  selector: 'app-person',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './person.component.html',
  styleUrl: './person.component.css',
})
export class PersonComponent {

  personForm: FormGroup;
  constructor(private fb: FormBuilder) {
    this.personForm = this.fb.group({
      name: ['', Validators.required],
      age: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      phone: ['', [Validators.required, Validators.pattern('^[0-9]+$')]],
    });
  }
  submit() {
    console.log(this.personForm.value);
    this.personForm.valid;
    if (this.personForm.valid) {
      alert('Form Submitted succesfully!!!');
    } else {
      Object.keys(this.personForm.controls).forEach((key) => {
        const control = this.personForm.get(key);
        control?.markAsTouched();
      });
    }
  }
}
