import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Apollo, gql } from 'apollo-angular';
import { map, Observable } from 'rxjs';
import { Applications } from 'src/app/models/applications';

const API_URL = 'http://localhost:8080/api/application';

@Injectable({
  providedIn: 'root'
})
export class ApplicationService {

  constructor(private http: HttpClient, private apollo: Apollo) { }

  public getAllApplications(): Observable<Applications[]>{
    return this.apollo.watchQuery<any>({
      query:gql`
        query getAllApplications{
          getAllApplications{
            id
            name
            code
            clientId
          }
        }
      `,
    })
      .valueChanges.pipe(map((result)=>result.data.getAllApplications));
  }

  public addApplication(form:any): Observable<any>{
    return this.http.post(API_URL+'/admin/add',form);
  }

  public deleteApplication(applicationId: string): Observable<any>{
    const params = new HttpParams().set('applicationId',applicationId);
    return this.http.delete(API_URL+'/admin/delete',{params:params});
  }
}
