import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { catchError } from 'rxjs/operators';
import { Configuration } from '../models/configuration';

@Injectable({
  providedIn: 'root'
})
export class BackendService {

  constructor(private http: HttpClient) {
  }

  getConfiguration(): Observable<Configuration> {
    return this.http.get<Configuration>('/api/configuration').pipe(
      catchError(error => {
        console.log(error);
        return throwError(error.error);
      })
    );
  }
}
