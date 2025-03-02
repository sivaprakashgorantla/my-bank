import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root' // This makes it available globally
})
export class LoanSerciceService {
  private url: string = 'http://localhost:7878/api/v1/loans/';

  constructor(private httpClient: HttpClient) {}

}
