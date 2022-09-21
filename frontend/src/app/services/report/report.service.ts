import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

const API_URL = 'http://localhost:8080/api/report';

@Injectable({
  providedIn: 'root'
})
export class ReportService {

  constructor(private http: HttpClient) { }

  public generateReportFromDatabase(): Observable<any>{
    return this.http.get(API_URL+'/generate/database');
  }

  public generateReportFromFile(): Observable<any>{
    return this.http.get(API_URL+'/generate/file');
  }
}
