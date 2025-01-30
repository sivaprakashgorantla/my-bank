import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { Router } from '@angular/router';
import { catchError, tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private authApiUrl = 'http://localhost:7878/api/v1/auth/';
  private userApiUrl = 'http://localhost:7878/api/v1/users/'; // Replace with your API URL
  private customerApiUrl = 'http://localhost:7878/api/v1/customers/'; // Replace with your API URL
  private tokenKey = 'auth-token'; // Store the JWT token in local storage

  constructor(private http: HttpClient, private router: Router) {}

  getUserProfile(): Observable<any> {
    const userId = localStorage.getItem('userId');
    const token = localStorage.getItem('auth-token');
    if (!token) {
      console.error('JWT token is not available in localStorage');
      throw new Error('User is not authenticated');
    }

    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`,
    });
    return this.http.get<any>(`${this.userApiUrl}${userId}`, { headers });
  }

  updateUserProfile(profile: any): Observable<any> {
    const userId = localStorage.getItem('userId');
    const token = localStorage.getItem('auth-token');

    if (!userId || !token) {
      console.error('User ID or JWT token is missing in localStorage');
      return throwError(() => new Error('User is not authenticated.'));
    }

    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`,
      'Content-Type': 'application/json',
    });

    return this.http
      .put<any>(`${this.userApiUrl}${userId}`, profile, { headers })
      .pipe(
        tap(() => {
          console.log('updateUserProfile successful for userId:', userId);
        }),
        catchError((error) => {
          console.error('Error updating user profile:', error);
          return throwError(() => new Error('Failed to update user profile.'));
        })
      );
  }

  // Send OTP to the user (Email/Phone)
  sendOtp(userId: number): Observable<any> {
    const token = localStorage.getItem('auth-token');
    if (!token) {
      console.error('JWT token is not available in localStorage');
      return throwError(() => new Error('User is not authenticated.'));
    }

    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`,
      'Content-Type': 'application/json',
    });

    return this.http.post<any>(
      `${this.userApiUrl}send-otp`,
      { userId },
      { headers }
    ).pipe(
      tap((response) => {
        console.log('OTP sent successfully:', response);
      }),
      catchError((error) => {
        console.error('Error sending OTP:', error);
        return throwError(() => new Error('Failed to send OTP.'));
      })
    );
  }

  // Validate the OTP entered by the user
  validateOtp(userId: number, otp: string): Observable<any> {
    const token = localStorage.getItem('auth-token');
    if (!token) {
      console.error('JWT token is not available in localStorage');
      return throwError(() => new Error('User is not authenticated.'));
    }

    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`,
      'Content-Type': 'application/json',
    });

    return this.http.post<any>(
      `${this.userApiUrl}profile-verify-otp`,
      { userId, otp },
      { headers ,responseType: 'text' as 'json'},

    ).pipe(
      tap((response) => {
        console.log('OTP validated successfully:', response);
      }),
      catchError((error) => {
        console.error('Error validating OTP:', error);
        return throwError(() => new Error('Failed to validate OTP.'));
      })
    );
  }

  // Save the JWT token to local storage
  saveToken(token: string): void {
    localStorage.setItem(this.tokenKey, token);
  }

  // Retrieve the JWT token from local storage
  getToken(): string | null {
    const token = localStorage.getItem(this.tokenKey);
    return token;
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
    return true;  // Implement token expiration check if needed
  }

  // Handle errors and return an observable with a user-friendly message
  private handleError(error: any): Observable<never> {
    let errorMessage = 'An error occurred during authentication. Please try again later.';
    if (error.status === 0) {
      errorMessage = 'Network error. Please check your internet connection.';
    } else if (error.status === 401) {
      errorMessage = 'Invalid credentials. Please check your email and password.';
    }
    alert(errorMessage);
    return throwError(errorMessage);
  }
}
