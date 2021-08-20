import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Observable, of} from 'rxjs';

@Injectable({ providedIn: 'root' })
export class UpdateDirectoryService {

  private url = '/api/asyncapi';  // URL to web api

  httpOptions = {
    headers: { 'Content-Type': 'application/json' }
  };

  constructor(
    private http: HttpClient) { }

  getAsyncApiSummary(): Observable<any[]> {
    const url = `${this.url}/`;
    return this.http.get<any[]>(url);
  }
}

