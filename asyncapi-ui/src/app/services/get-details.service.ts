import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Observable, of} from 'rxjs';

@Injectable({ providedIn: 'root' })
export class GetDetailsService {

  private url = '/api/kafka';  // URL to web api

  httpOptions = {
    headers: { 'Content-Type': 'application/json' }
  };

  constructor(
    private http: HttpClient) { }

  /* GET: all methods to SHOW an object from the blockchain */
  getSpecificApiDefinition(artifactId: string): Observable<string> {
    const url = `${this.url}/asyncapi/${artifactId}/latest`;
    return this.http.get<string>(url);
  }
}
