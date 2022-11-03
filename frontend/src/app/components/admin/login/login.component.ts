import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { AccountAuthService } from 'src/app/services/auth/account-auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  loginForm: FormGroup;

  constructor(private route:ActivatedRoute, private router:Router, private accountAuthService:AccountAuthService, private formBuilder:FormBuilder) {
    if(this.accountAuthService.accountValue){
      this.router.navigate(['/admin']);
    }
  }

  ngOnInit(): void {
    this.generateLoginForm();
  }

  
  generateLoginForm(){
    this.loginForm = this.formBuilder.group({
      username: [null, [Validators.required]],
      password: [null, [Validators.required, Validators.minLength(6)]],
    });
  }

  get username(){
    return this.loginForm.get('username');
  }

  get password(){
    return this.loginForm.get('password');
  }

  login(){
    if(this.loginForm.valid){
      this.accountAuthService.login(this.loginForm.value).subscribe({
        next: () => {
          const returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/admin';
          this.router.navigateByUrl(returnUrl);
        },
        error: (error: any) => {
          console.log(error);
        }
      });
    }
  }

}
