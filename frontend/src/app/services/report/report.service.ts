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
              name
            }
            query
            title
            reportId
            createdAt
            updatedAt
          }
        }
      `,
    })
      .valueChanges.pipe(map((result)=>result.data.getAllReports));
  }

  public getReportByReportId(reportId:any): Observable<Reports>{
    return this.apollo.watchQuery<any>({
      query:gql`
        query getReportByReportId($reportId:String!){
          getReportByReportId(reportId: $reportId){
            id
            title
            reportConfig
          }
        }
      `,
      variables:{
        reportId: reportId,
      }
    })
      .valueChanges.pipe(map((result)=>result.data.getReportByReportId));
  }

  public addReport(form:any): Observable<any>{
    return this.http.post(API_URL+'/admin/add',form);
  }
  
  public editReport(reportId:string,form:any): Observable<any>{
    const params = new HttpParams().set('reportId',reportId);
    return this.http.put(API_URL+'/admin/edit',form,{params:params});
  }

  public saveReport(reportId:string,report:any): Observable<any>{
    const params = new HttpParams().set('reportId',reportId);
    return this.http.put(API_URL+'/save',report,{params:params});
  }

  public deleteReport(reportId:string): Observable<any>{
    const params = new HttpParams().set('reportId',reportId);
    return this.http.delete(API_URL+'/admin/delete',{params:params});
  }

  public generateReport(reportId:string): Observable<any>{
    const params = new HttpParams().set('reportId',reportId);
    return this.http.get(API_URL+'/generate',{params:params});
  }
  
}
