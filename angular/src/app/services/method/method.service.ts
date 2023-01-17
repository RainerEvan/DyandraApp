import { Injectable } from '@angular/core';
import { Apollo, gql } from 'apollo-angular';
import { map, Observable } from 'rxjs';
import { Methods } from 'src/app/models/methods';

@Injectable({
  providedIn: 'root'
})
export class MethodService {

  constructor(private apollo: Apollo) { }

  public getAllMethods(): Observable<Methods[]>{
    return this.apollo.watchQuery<any>({
      query:gql`
        query getAllMethods{
          getAllMethods{
            id
            name
          }
        }
      `,
    })
      .valueChanges.pipe(map((result)=>result.data.getAllMethods));
  }
}
