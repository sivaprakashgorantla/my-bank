import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AnyCatcher } from 'rxjs/internal/AnyCatcher';


export interface Transaction {
  transactionId: number;
  transactionType: string;
  counterpartyBank: string;
  counterpartyAccountId: string;
  transactionDate: string; // Use ISO 8601 format for dates
  amount: number;
  currencyCode: string;
  status: string;
  description: string;
}
@Injectable({
  providedIn: 'root'
})
export class TransactionsService {
  private baseUrl = 'http://localhost:7878/api/v1/transactions'; // Update with your backend URL

  constructor(private http: HttpClient) {}

  getAccountsByAccountNumber(accountNumber: string): Observable<any> {
    // Retrieve the JWT token from localStorage
    const token = localStorage.getItem('auth-token');
    console.log('Token in getAccountsByAccountNumber:', token); // Debugging token retrieval
    console.log('accountNumber in getAccountsByAccountNumber:', accountNumber); // Debugging userId retrieval

    if (!token) {
      console.error('JWT token is not available in localStorage');
      throw new Error('User is not authenticated');
    }

    // Add the Authorization header
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`,
    });

    // Return the HTTP GET request with headers
    return this.http.get<any>(`${this.baseUrl}/${accountNumber}/recent`, {
      headers,
    });
  }
}