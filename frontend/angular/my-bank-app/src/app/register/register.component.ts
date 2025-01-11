import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { ReactiveFormsModule, FormBuilder, Validators, FormGroup } from '@angular/forms';
import { AuthService } from '../auth.service';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [ReactiveFormsModule,CommonModule],
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent {
  
  registerForm: FormGroup
  

  constructor(private fb: FormBuilder,private authService: AuthService) {

    this.registerForm = this.fb.group({
      username:['', Validators.required],
      firstName:['', Validators.required],
      lastName:['', Validators.required],
      phoneNumber:['', Validators.required],
      name: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(4)]],
      confirmPassword: ['', Validators.required],
    }, { 
      validators: this.passwordMatchValidator
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
            },
            (error) => {
              console.error('Registration error:', error);
            }
          );
  //    }
    //}
  }
}
