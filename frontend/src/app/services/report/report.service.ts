import { HttpClient, HttpParams } from '@angular/common/http';
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

  public getAllReports(): Observable<Reports[]>{
    return this.apollo.watchQuery<any>({
      query:gql`
        query getAllReports{
          getAllReports{
            id
            connection{
              name
            }
            sourcePath{
              path
            }
            query
            title
            report
            reportId
            createdAt
            updatedAt
          }
        }
      `,
    })
      .valueChanges.pipe(map((result)=>result.data.getAllReports));
  }

  public getReport(reportId:any): Observable<Reports>{
    return this.apollo.watchQuery<any>({
      query:gql`
        query getReport($reportId:ID!){
          getReport(reportId: $reportId){
            id
            title
            report
          }
        }
      `,
      variables:{
        reportId: reportId,
      }
    })
      .valueChanges.pipe(map((result)=>result.data.getReport));
  }

  public addReport(form:any): Observable<any>{
    return this.http.post(API_URL+'/add',form);
  }

  public deleteReport(reportId: string): Observable<any>{
    const params = new HttpParams().set('reportId',reportId);
    return this.http.delete(API_URL+'/delete',{params:params});
  }

  public generateReportFromDatabase(): Observable<any>{
    return this.http.get(API_URL+'/generate/database');
  }

  public generateReportFromFile(): Observable<any>{
    return this.http.get(API_URL+'/generate/file');
  }

  public saveReport(report:any): Observable<any>{
    return this.http.post(API_URL+'/save',report);
  }
}
