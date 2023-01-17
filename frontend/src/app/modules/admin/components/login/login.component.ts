import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginForm: FormGroup;

  constructor(private route:ActivatedRoute, private router:Router, private authService:AuthService, private formBuilder:FormBuilder) {
    if(this.authService.accountValue){
      this.router.navigate(['/admin']);
    }
  }

  ngOnInit(): void {
    this.generateLoginForm();
  }

  
  generateLoginForm(){
    this.loginForm = this.formBuilder.group({
      userId: [null, [Validators.required]],
      password: [null, [Validators.required, Validators.minLength(6)]],
    });
  }

  get userId(){
    return this.loginForm.get('userId');
  }

  get password(){
    return this.loginForm.get('password');
  }

  login(){
    if(this.loginForm.valid){
      this.authService.login(this.loginForm.value).subscribe({
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
