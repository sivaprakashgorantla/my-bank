import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { Router } from '@angular/router';
import { catchError } from 'rxjs/operators';

interface JwtPayload {
  sub: string;
  userid: number;
  exp: number;
  // add other expected claims here
}

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private authApiUrl = 'http://localhost:7878/api/v1/auth/'; // Replace with your API URL
  private userApiUrl = 'http://localhost:7878/api/v1/users/'; // Replace with your API URL
  private tokenKey = 'auth-token'; // Store the JWT token in local storage

  constructor(private http: HttpClient, private router: Router) {}

  // Login method
  login(credentials: any): void {
    this.http
      .post('http://localhost:7878/api/v1/auth/login', credentials)
      .subscribe((response: any) => {
        const { token, userId, username, customerId } = response;
        this.saveToken(token);

        localStorage.setItem('userId', userId);
        localStorage.setItem('username', username);
        localStorage.setItem('customerId', customerId);
        this.router.navigate(['/dashboard']);
      });
  }

  // login(username: string, password: string): Observable<any> {
  //   const loginPayload = { username, password };
  //   return this.http.post<any>(`${this.authApiUrl}login`, loginPayload).pipe(
  //     catchError((error) => this.handleError(error))
  //   );
  // }

  // Register method
  register(
    username: string,
    firstName: string,
    lastName: string,
    email: string,
    phoneNumber: string,
    password: string
  ): Observable<any> {
    const registerPayload = {
      username,
      firstName,
      lastName,
      email,
      phoneNumber,
      password,
    };
    return this.http
      .post<any>(`${this.authApiUrl}register`, registerPayload)
      .pipe(catchError((error) => this.handleError(error)));
  }

  getUserProfile(): Observable<any> {
    const userId = localStorage.getItem('userId'); // Assuming userId is stored in localStorage
    const token = localStorage.getItem('auth-token'); // Retrieve the JWT token from localStorage
    console.log('Token in getUserProfile:', token); // Debugging token retrieval
    console.log('userId in getUserProfile:', userId); // Debugging userId retrieval
    if (!token) {
      console.error('JWT token is not available in localStorage');
      throw new Error('User is not authenticated');
    }

    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`, // Add the Authorization header
    });
    return this.http.get<any>(`${this.userApiUrl}85`, { headers });
  }

  updateUserProfile(profile: any): Observable<any> {
    console.log('updateUserProfile..................');
    const userId = localStorage.getItem('userId');
    return this.http.put<any>(`${this.userApiUrl}/${userId}`, profile);
  }

  // Save the JWT token to local storage
  saveToken(token: string): void {
    localStorage.setItem(this.tokenKey, token);
  }

  // Retrieve the JWT token from local storage
  getToken(): string | null {
    const token = localStorage.getItem(this.tokenKey);
    console.log('Retrieved token:', token); // Debugging token retrieval
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
    return true;
  }

  // Handle errors and return an observable with a user-friendly message
  private handleError(error: any): Observable<never> {
    console.error('AuthService Error:', error);

    let errorMessage =
      'An error occurred during authentication. Please try again later.';

    if (error.status === 0) {
      errorMessage = 'Network error. Please check your internet connection.';
    } else if (error.status === 401) {
      errorMessage =
        'Invalid credentials. Please check your email and password.';
    } else if (error.status === 400) {
      errorMessage = 'Invalid input. Please check the registration details.';
    }

    alert(errorMessage);
    return throwError(errorMessage); // Return an observable with the error message
  }
}
