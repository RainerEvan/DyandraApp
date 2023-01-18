import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Apollo, gql } from 'apollo-angular';
import { map, Observable } from 'rxjs';
import { Connections } from 'src/app/models/connections';
import { environment } from 'src/environments/environment';

const API_URL = environment.apiUrl + '/admin/connection';

@Injectable({
  providedIn: 'root'
})
export class ConnectionService {

  constructor(private http: HttpClient, private apollo: Apollo) { }

  public getAllConnections(): Observable<Connections[]>{
    return this.apollo.watchQuery<any>({
      query:gql`
        query getAllConnections{
          getAllConnections{
            id
            application{
              name
            }
            method{
              name
            }
            name
            createdAt
          }
        }
      `,
    })
      .valueChanges.pipe(map((result)=>result.data.getAllConnections));
  }

  public addConnection(form:any): Observable<any>{
    return this.http.post(API_URL+'/add',form);
  }

  public deleteConnection(connectionId: string): Observable<any>{
    const params = new HttpParams().set('connectionId',connectionId);
    return this.http.delete(API_URL+'/delete',{params:params});
  }
}
