import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Observable, of} from 'rxjs';

@Injectable({ providedIn: 'root' })
export class UpdateDashboardService {

  private url = '/api/kafka';  // URL to web api

  httpOptions = {
    headers: { 'Content-Type': 'application/json' }
  };

  constructor(
    private http: HttpClient) { }

  /* GET: all methods to SHOW an object from the blockchain */
  getAsyncApiSummary(): Observable<string[]> {
    const url = `${this.url}/asyncapi/`;
    return this.http.get<string[]>(url);
  }
}

