import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class LoanSerciceService {
  
  url: string = 'http://localhost:7878/api/v1/loans/';
  constructor(private httpClient: HttpClient) {}

  applyForLoan(
    loanAmount: number,
    loanPurpose: string,
    termMonths: number,
    loanType: string,
  ): Observable<any> {
    const token = localStorage.getItem('auth-token');
    const customerId: number = parseInt(
      localStorage.getItem('customerId')!,
      10
    );

    if (!token) {
      console.error('JWT token is not available in localStorage');
      throw new Error('User is not authenticated');
    }

    const headers = new HttpHeaders({ Authorization: `Bearer ${token}` });

    const applyLoan = { customerId, loanAmount, loanPurpose, termMonths ,loanType};

    return this.httpClient.post<any>(`${this.url}apply`, applyLoan, {
      headers,
    });
  }

  // applyForLoan(loanAmount:string, loanPurpose: string,  numEMIs:string):Observable<any>{
  //   console.log("loanAmount",loanAmount);
  //   console.log("loanPurpose",loanPurpose);
  //   console.log("numEMIs",numEMIs);
  //   const token = localStorage.getItem('auth-token');
  //       console.log('Token in getTransactionsByAccountNumber:', token); // Debugging token retrieval
  //       console.log('accountNumber in getTransactionsByAccountNumber:'); // Debugging userId retrieval

  //       if (!token) {
  //         console.error('JWT token is not available in localStorage');
  //         throw new Error('User is not authenticated');
  //       }

  //       // Add the Authorization header
  //       const headers = new HttpHeaders({
  //         Authorization: `Bearer ${token}`,
  //       });

  //       const applyLoan = {
  //         loanAmount,
  //         loanPurpose,
  //         numEMIs
  //       };
  //   return this.httpClient.post<any>(`${this.url}apply`, applyLoan,{
  //     headers,
  //   });
  // }
  approveLoan(loanId: any, remarkInput: string,status:string): Observable<any> {
    console.log('Loan ID:', loanId);
    console.log('Remark:', remarkInput);
    console.log('Status:', status);
    const token = localStorage.getItem('auth-token');
    console.log('Token in getTransactionsByAccountNumber:', token); // Debugging token retrieval
    console.log('accountNumber in getTransactionsByAccountNumber:'); // Debugging userId retrieval

    if (!token) {
      console.error('JWT token is not available in localStorage');
      throw new Error('User is not authenticated');
    }

    // Add the Authorization header
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`,
    });

    const loanApplication = {
      loanId,
      remarkInput,
      status
    };

    return this.httpClient.put<any>(`${this.url}approve`, loanApplication,{
      headers,
    });
  }
  getApprovals(): Observable<any> {
    const token = localStorage.getItem('auth-token');
    console.log('Token in getTransactionsByAccountNumber:', token); // Debugging token retrieval
    console.log('accountNumber in getTransactionsByAccountNumber:'); // Debugging userId retrieval

    if (!token) {
      console.error('JWT token is not available in localStorage');
      throw new Error('User is not authenticated');
    }

    // Add the Authorization header
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`,
    });

    return this.httpClient.get<any>(`${this.url}allApproval`, {
      headers,
    });
  }

  // Fetch loan types from backend
  getLoanTypes(): Observable<string[]> {
    const token = localStorage.getItem('auth-token');
    const customerId: number = parseInt(
      localStorage.getItem('customerId')!,
      10
    );

    if (!token) {
      console.error('JWT token is not available in localStorage');
      throw new Error('User is not authenticated');
    }

    const headers = new HttpHeaders({ Authorization: `Bearer ${token}` });

    return this.httpClient.get<string[]>(`${this.url}loan-types`, { headers });
  }

  getInterestRate(loanType: string): Observable<number> {
    const token = localStorage.getItem('auth-token');
    if (!token) {
      console.error('JWT token is not available in localStorage');
      throw new Error('User is not authenticated');
    }

    const headers = new HttpHeaders({ Authorization: `Bearer ${token}` });

    return this.httpClient.get<number>(`${this.url}interest-rate/${loanType}`, {
      headers,
    });
  }
  getLoanDetails(loanId: string): Observable<any> {
    return this.httpClient.get(`${this.url}status/${loanId}`);
  }
}
