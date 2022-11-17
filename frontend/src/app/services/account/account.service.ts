import { Injectable } from '@angular/core';
import { Apollo, gql } from 'apollo-angular';
import { map, Observable } from 'rxjs';
import { Accounts } from 'src/app/models/accounts';

@Injectable({
  providedIn: 'root'
})
export class AccountService {

  constructor(private apollo: Apollo) { }

  public getAccountByUserId(userId:any): Observable<Accounts>{
    return this.apollo.watchQuery<any>({
      query:gql`
        query getAccountByUserId($userId:String!){
          getAccountByUserId(userId: $userId){
            id
            userId
            hostName
            isActive
            role{
              name
            }
          }
        }
      `,
      variables:{
        userId: userId,
      }
    })
      .valueChanges.pipe(map((result)=>result.data.getAccountByUserId));
  }
}
