import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { BehaviorSubject, map, Observable } from 'rxjs';
import { AccountAuthDetails } from 'src/app/models/accountauthdetails';

const API_URL = 'http://localhost:8080/api/auth';

@Injectable({
  providedIn: 'root'
})
export class AccountAuthService {

  private accountSubject: BehaviorSubject<AccountAuthDetails>;

  constructor(private router:Router, private http:HttpClient) {
    this.accountSubject = new BehaviorSubject<AccountAuthDetails>(JSON.parse(sessionStorage.getItem('account')));
  }

  public get accountValue(): AccountAuthDetails{
    return this.accountSubject.value;
  }

  public login(form:any): Observable<any>{
    return this.http.post(API_URL+'/login',form).pipe(
      map((account:any) => {
        sessionStorage.setItem('account',JSON.stringify(account));
        this.accountSubject.next(account);
      })
    );
  }

  public logout(){
    sessionStorage.removeItem('account');
    this.accountSubject.next(null);
    this.router.navigate(['/admin/login']);
  }
}
