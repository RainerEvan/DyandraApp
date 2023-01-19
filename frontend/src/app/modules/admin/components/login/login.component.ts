import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { DialogService, DynamicDialogRef } from 'primeng/dynamicdialog';
import { ResultDialogComponent } from 'src/app/modules/shared/components/result-dialog/result-dialog.component';
import { AuthService } from 'src/app/services/auth/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginForm: FormGroup;
  ref:DynamicDialogRef;

  constructor(public dialogService:DialogService, private route:ActivatedRoute, private router:Router, private authService:AuthService, private formBuilder:FormBuilder) {
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
          this.showResultDialog("Success","Login Success! Welcome back");

          const returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/admin';
          this.router.navigateByUrl(returnUrl);
        },
        error: (error: any) => {
          console.log(error);
          this.showResultDialog("Failed",error.message);
        }
      });
    }
  }

  showResultDialog(title:string, message:string){
    this.ref = this.dialogService.open(ResultDialogComponent, {
      header: title,
      data: {
        message: message,
      },
      baseZIndex: 10000,
      contentStyle: {"max-height": "650px","width":"40vw", "min-width":"250px", "max-width":"400px", "overflow": "auto"},
    });
  }
}
