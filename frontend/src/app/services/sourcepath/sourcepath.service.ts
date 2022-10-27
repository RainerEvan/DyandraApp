import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Apollo, gql } from 'apollo-angular';
import { map, Observable } from 'rxjs';
import { SourcePaths } from 'src/app/models/sourcepaths';

const API_URL = 'http://localhost:8080/api/source-path';

@Injectable({
  providedIn: 'root'
})
export class SourcepathService {

  constructor(private http: HttpClient, private apollo: Apollo) { }

  public getAllSourcePaths(): Observable<SourcePaths[]>{
    return this.apollo.watchQuery<any>({
      query:gql`
        query getAllSourcePaths{
          getAllSourcePaths{
            id
            name
            connection{
              name
            }
            path
            username
            password
            createdAt
          }
        }
      `,
    })
      .valueChanges.pipe(map((result)=>result.data.getAllSourcePaths));
  }

  public getAllSourcePathsForConnection(connectionId:string): Observable<SourcePaths[]>{
    return this.apollo.watchQuery<any>({
      query:gql`
        query getAllSourcePathsForConnection($connectionId:ID!){
          getAllSourcePathsForConnection(connectionId: $connectionId){
            id
            path
          }
        }
      `,
      variables: {
        connectionId: connectionId,
      },
    })
      .valueChanges.pipe(map((result)=>result.data.getAllSourcePathsForConnection));
  }

  public addSourcePath(form:any): Observable<any>{
    return this.http.post(API_URL+'/add',form);
  }

  public editSourcePath(sourcePathId: string, form:any): Observable<any>{
    const params = new HttpParams().set('sourcePathId',sourcePathId);
    return this.http.put(API_URL+'/edit',form,{params:params});
  }
  public deleteSourcePath(sourcePathId: string): Observable<any>{
    const params = new HttpParams().set('sourcePathId',sourcePathId);
    return this.http.delete(API_URL+'/delete',{params:params});
  }
}
