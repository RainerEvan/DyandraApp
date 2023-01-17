import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { DynamicDialogRef } from 'primeng/dynamicdialog';
import { ApplicationService } from 'src/app/services/application/application.service';

@Component({
  selector: 'app-add-application',
  templateUrl: './add-application.component.html',
  styleUrls: ['./add-application.component.scss']
})
export class AddApplicationComponent implements OnInit {

  applicationForm: FormGroup;
  isApplicationFormSubmitted: boolean = false;
  
  constructor(public ref: DynamicDialogRef,private applicationService: ApplicationService, private formBuilder: FormBuilder) { }

  ngOnInit(): void {
    this.generateApplicationForm();
  }

  public addApplication(): void{
    if(this.applicationForm.valid){
      const application = this.applicationForm.value;

      this.applicationService.addApplication(application).subscribe({
        next: (result: any) => {
          this.isApplicationFormSubmitted = true;
          this.ref.close(this.isApplicationFormSubmitted);
          console.log(result);
        },
        error: (error: any) => {
          this.isApplicationFormSubmitted = false;
          this.ref.close(this.isApplicationFormSubmitted);
          console.log(error);
        }
      });
    } 
  }

  generateApplicationForm(){
    this.applicationForm = this.formBuilder.group({
      name: [null, [Validators.required]],
      code: [null, [Validators.required]],
    });
  }

  get name(){
    return this.applicationForm.get('name');
  }
  get code(){
    return this.applicationForm.get('code');
  }

}
