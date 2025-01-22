import { CommonModule, NgIf } from '@angular/common';
import { Component } from '@angular/core';
import { ReactiveFormsModule, FormBuilder, Validators, FormGroup } from '@angular/forms';
import { AuthService } from '../auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [ReactiveFormsModule,CommonModule,NgIf],
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent {
  
  registerForm: FormGroup
  

  constructor(private fb: FormBuilder,private authService: AuthService, private router: Router) {

    this.registerForm = this.fb.group({
      username: ['', Validators.required],
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      phoneNumber: ['', [Validators.required, Validators.pattern(/^[0-9]{10}$/)]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(4)]],
      confirmPassword: ['', Validators.required],
    }, {
      validators: this.passwordMatchValidator // password = password
    });

  }

  passwordMatchValidator(group: any) {
    const { password, confirmPassword } = group.controls;
    return password.value === confirmPassword.value ? null : { notMatching: true };
  }

  onSubmit() {
    console.log('on submit for register');
    //if (this.registerForm.valid) {
      console.log('Registration Data: ', this.registerForm.value);
    //  if (this.registerForm.valid) {
        console.log('Registration Data: ', this.registerForm.value);
        const { username, firstName, lastName, email, phoneNumber, password } = this.registerForm.value;
        this.authService
          .register(username, firstName, lastName, email, phoneNumber, password)
          .subscribe(
            (response) => {
              alert('Registration successful!');
              console.log(response);
              localStorage.setItem('phoneNumber', phoneNumber);
              localStorage.setItem('userId', response.userId);
              console.log('register submi userId:', response.userId);
              this.router.navigateByUrl('/app-user-otp-validation');
            },
            
            (error) => {
              console.error('Registration error:', error);
            }
          );
          
  }
}
