import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Observable, of} from 'rxjs';

@Injectable({ providedIn: 'root' })
export class GetDetailsService {

  private url = '/api/asyncapi';  // URL to web api

  httpOptions = {
    headers: { 'Content-Type': 'application/json' }
  };

  constructor(
    private http: HttpClient) { }

  getLatestApiDefinition(artifactId: string): Observable<any> {
    const url = `${this.url}/${artifactId}/latest`;
    return this.http.get<any>(url);
  }

  getLatestVersionNumber(artifactId: string): Observable<string> {
    const url = `${this.url}/${artifactId}`;
    return this.http.get(url, { responseType: 'text' });
  }

  getSpecificApiDefinition(artifactId: string, version: number): Observable<any> {
    const url = `${this.url}/${artifactId}/${version}`;
    return this.http.get<any>(url);
  }
}
