import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../auth.service';
import { HttpClientModule } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule, HttpClientModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {

  loginForm: FormGroup;
  loading = false;
  errorMessage = '';

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) {
    this.loginForm = this.fb.group({
      username: ['', [Validators.required]], // Updated form control
      password: ['', [Validators.required, Validators.minLength(6)]]
    });
  }

  onSubmit() {
    if (this.loginForm.valid) {
      this.loading = true;
      this.errorMessage = '';

      // Call the login method from AuthService
      this.authService.login(this.loginForm.value.username, this.loginForm.value.password) // Updated to use username
        .subscribe({
          next: (response) => {
            console.log('Login Response: ', response);
            this.authService.saveToken(response.token);
            this.router.navigate(['/dashboard']);
          },
          error: (error) => {
            console.error('Login Error:', error);
            this.errorMessage = 'Invalid credentials, please try again.';
            this.loading = false;
          },
          complete: () => {
            this.loading = false;
          }
        });
    } else {
      this.errorMessage = 'Please fill in all fields correctly.';
    }
  }
}
