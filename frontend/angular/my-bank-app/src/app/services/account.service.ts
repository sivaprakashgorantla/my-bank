
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface AccountDetailsDTO {
  accountId: number;
  accountType: string;
  accountNumber: string;
  balance: number;
  currencyCode: string;
  status: string;
}

export interface AccountResponseDTO {
  message: string;
  accounts: AccountDetailsDTO[] | null;
}

@Injectable({
  providedIn: 'root',
})
export class AccountService {
  private baseUrl = 'http://localhost:7878/api/v1/accounts'; // Update with your backend URL

  constructor(private http: HttpClient) { }

  getAccountsByCustomerId(customerId: string): Observable<AccountResponseDTO> {
    // Retrieve the JWT token from localStorage
    const token = localStorage.getItem('auth-token');
    console.log('Token in getAccountsByUserId:', token); // Debugging token retrieval
    console.log('customerId in getAccountsByCustomerId:', customerId); // Debugging userId retrieval

    if (!token) {
      console.error('JWT token is not available in localStorage');
      throw new Error('User is not authenticated');
    }

    // Add the Authorization header
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`,
    });

    // Return the HTTP GET request with headers
    return this.http.get<AccountResponseDTO>(`${this.baseUrl}/customer/${customerId}`, {
      headers,
    });
  }
}
