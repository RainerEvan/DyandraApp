import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Apollo, gql } from 'apollo-angular';
import { map, Observable } from 'rxjs';
import { Reports } from 'src/app/models/reports';

const API_URL = 'http://localhost:8080/api/report';

@Injectable({
  providedIn: 'root'
})
export class ReportService {

  constructor(private http: HttpClient, private apollo: Apollo) { }

  public getAllAccounts(): Observable<Reports[]>{
    return this.apollo.watchQuery<any>({
      query:gql`
        query getAllReports{
          getAllReports{
            id
            application
            title
            report
            createdAt
          }
        }
      `,
    })
      .valueChanges.pipe(map((result)=>result.data.getAllReports));
  }

  public generateReportFromDatabase(): Observable<any>{
    return this.http.get(API_URL+'/generate/database');
  }

  public generateReportFromFile(): Observable<any>{
    return this.http.get(API_URL+'/generate/file');
  }

  public saveReport(reportJson:any): Observable<any>{
    return this.http.post(API_URL+'/save',reportJson);
  }
}
