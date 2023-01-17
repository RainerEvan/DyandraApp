import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

const API_URL = environment.apiUrl+'/public/report';

@Injectable({
  providedIn: 'root'
})
export class ClientService {
  
  constructor(private http: HttpClient) { }
  
  public generateReport(reportId:string): Observable<any>{
    const params = new HttpParams().set('reportId',reportId);
    return this.http.get(API_URL+'/generate',{params:params});
  }

  public saveReport(reportId:string,report:any): Observable<any>{
    const params = new HttpParams().set('reportId',reportId);
    return this.http.put(API_URL+'/save',report,{params:params});
  }
}
