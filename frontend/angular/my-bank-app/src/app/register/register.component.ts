import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { ReactiveFormsModule, FormBuilder, Validators, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [ReactiveFormsModule,CommonModule],
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent {
  
  registerForm: FormGroup
  

  constructor(private fb: FormBuilder) {

    this.registerForm = this.fb.group({
      name: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      confirmPassword: ['', Validators.required]
    }, { 
      validators: this.passwordMatchValidator
    });

  }

  passwordMatchValidator(group: any) {
    const { password, confirmPassword } = group.controls;
    return password.value === confirmPassword.value ? null : { notMatching: true };
  }

  onSubmit() {
    if (this.registerForm.valid) {
      console.log('Registration Data: ', this.registerForm.value);
    }
  }
}
