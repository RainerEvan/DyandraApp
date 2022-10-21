import { HttpClient } from '@angular/common/http';
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
            connection{
              name
            }
            path
            createdAt
          }
        }
      `,
    })
      .valueChanges.pipe(map((result)=>result.data.getAllSourcePaths));
  }

  public addSourcePath(form:any): Observable<any>{
    return this.http.post(API_URL+'/add',form);
  }
}
