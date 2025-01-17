import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root' // Makes the service globally available
})
export class BeneficiaryService {
  private baseUrl = 'http://localhost:7878/api/v1/beneficiaries/';

  constructor(private http: HttpClient) { }

  // Get all beneficiaries
  getBeneficiaries(): Observable<any[]> {
    const token = localStorage.getItem('auth-token');
    console.log('Token in getBeneficiaries:', token); // Debugging token retrieval
    console.log('accountNumber in getBeneficiaries:'); // Debugging userId retrieval

    if (!token) {
      console.error('JWT token is not available in localStorage');
      throw new Error('User is not authenticated');
    }

    // Add the Authorization header
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`,
    });

    const userId = localStorage.getItem('userId');
    return this.http.get<any[]>(`${this.baseUrl}${userId}`, { headers });
  }

  // Add a new beneficiary
  addBeneficiary(beneficiary: any): Observable<any> {
    console.log('Beneficiary addBeneficiary:', beneficiary);
    const token = localStorage.getItem('auth-token');
    console.log('Token in getAccountsByAccountNumber:', token); // Debugging token retrieval
    console.log('accountNumber in getAccountsByAccountNumber:'); // Debugging userId retrieval

    if (!token) {
      console.error('JWT token is not available in localStorage');
      throw new Error('User is not authenticated');
    }

    // Add the Authorization header
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`,
    });
    console.log(' addBeneficiary Beneficiary 1:', beneficiary);
    //return this.http.post<any>(`${this.baseUrl}addBeneficiary`, headers,beneficiary);
    return this.http.post<any>(`${this.baseUrl}addBeneficiary`, beneficiary, { headers });
  }

  updateBeneficiary(beneficiaryId: number, beneficiary: any): Observable<any> {
    console.log('deleteBeneficiary :', beneficiaryId);
    const token = localStorage.getItem('auth-token');
    console.log('Token in getAccountsByAccountNumber:', token);
    console.log('accountNumber in getAccountsByAccountNumber:');

    if (!token) {
      console.error('JWT token is not available in localStorage');
      throw new Error('User is not authenticated');
    }

    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`,
    });

    return this.http.put<any>(`${this.baseUrl}/beneficiaries/${beneficiaryId}`, beneficiary);
  }


  // Delete a beneficiary
  deleteBeneficiary(beneficiaryId: number): Observable<string> {
    console.log('deleteBeneficiary :', beneficiaryId);
    const token = localStorage.getItem('auth-token');
    console.log('Token in getAccountsByAccountNumber:', token); // Debugging token retrieval
    console.log('accountNumber in getAccountsByAccountNumber:'); // Debugging userId retrieval

    if (!token) {
      console.error('JWT token is not available in localStorage');
      throw new Error('User is not authenticated');
    }

    // Add the Authorization header
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`,
    });

    return this.http.delete(`${this.baseUrl}${beneficiaryId}`, {
      headers,
      responseType: 'text', // Specify the response type as 'text'
    });
  }

}
