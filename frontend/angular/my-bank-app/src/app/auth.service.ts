import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { Router } from '@angular/router';
import { catchError } from 'rxjs/operators';
// import { default as jwt_decode } from 'jwt-decode';


@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:7878/api/v1/users/'; // Replace with your API URL
  private tokenKey = 'auth-token'; // Store the JWT token in local storage

  constructor(private http: HttpClient, private router: Router) {}

  // Login method
  login(email: string, password: string): Observable<any> {
    const loginPayload = { email, password };
    const formData = new FormData();
    formData.append('email', email);
    formData.append('password', password);
    return this.http.post<any>(`${this.apiUrl}login`, formData)
      .pipe(
        catchError((error) => this.handleError(error))
      );
  }

  // Save the JWT token to local storage
  saveToken(token: string): void {
    localStorage.setItem(this.tokenKey, token);
  }

  // Retrieve the JWT token from local storage
  getToken(): string | null {
    return localStorage.getItem(this.tokenKey);
  }

  // Logout method
  logout(): void {
    localStorage.removeItem(this.tokenKey);
    this.router.navigate(['/login']);
  }

  // Check if the user is authenticated
  isAuthenticated(): boolean {
    const token = this.getToken();
    if (token) {
      return !this.isTokenExpired(token);
    }
    return false;
  }

  // Decode and check token expiration
  private isTokenExpired(token: string): boolean {
    // try {
    //   const decodedToken: any = jwt_decode(token);
    //   const expirationDate = new Date(decodedToken.exp * 1000);
    //   return expirationDate < new Date();
    // } catch (error) {
    //   return true;
    // }
    return true;
  }

  // Handle errors and return an observable with a user-friendly message
  private handleError(error: any): Observable<never> {
    console.error('AuthService Error:', error);

    // Return a user-friendly error message
    let errorMessage = 'An error occurred during authentication. Please try again later.';

    if (error.status === 0) {
      errorMessage = 'Network error. Please check your internet connection.';
    } else if (error.status === 401) {
      errorMessage = 'Invalid credentials. Please check your email and password.';
    }

    // You can show an alert or return the message through an observable
    alert(errorMessage);
    return throwError(errorMessage);  // Return an observable with the error message
  }
}
