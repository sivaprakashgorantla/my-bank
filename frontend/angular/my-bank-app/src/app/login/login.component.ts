import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../auth.service';
import { HttpClientModule } from '@angular/common/http';
import { Router } from '@angular/router'; // Import Router for redirection

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule, HttpClientModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {

  loginForm: FormGroup;
  loading = false;  // To show a loading indicator during the login process
  errorMessage = '';  // To display error messages to the user

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router  // Inject Router to navigate after login
  ) {
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]]
    });
  }

  onSubmit() {
    if (this.loginForm.valid) {
      this.loading = true;  // Set loading to true during login
      this.errorMessage = '';  // Clear any previous error messages

      // Call the login method from AuthService
      this.authService.login(this.loginForm.value.email, this.loginForm.value.password)
        .subscribe({
          next: (response) => {
            console.log('Login Response: ', response);
            this.authService.saveToken(response.token);  // Save the token
            this.router.navigate(['/dashboard']);  // Navigate to the dashboard (or any other page)
          },
          error: (error) => {
            console.error('Login Error:', error);
            this.errorMessage = 'Invalid credentials, please try again.';  // Show error message
            this.loading = false;  // Stop loading on error
          },
          complete: () => {
            this.loading = false;  // Stop loading once the request is complete
          }
        });

      console.log('Login Data: ', this.loginForm.value);
    } else {
      // If the form is invalid, you can show an appropriate error message
      this.errorMessage = 'Please fill in all fields correctly.';
    }
  }
}
