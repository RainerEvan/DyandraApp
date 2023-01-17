import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { DynamicDialogConfig, DynamicDialogRef } from 'primeng/dynamicdialog';
import { Applications } from 'src/app/models/applications';

@Component({
  selector: 'app-application-details',
  templateUrl: './application-details.component.html',
  styleUrls: ['./application-details.component.scss']
})
export class ApplicationDetailsComponent implements OnInit {

  applicationForm: FormGroup;
  application: Applications;
  
  constructor(public ref: DynamicDialogRef, public config: DynamicDialogConfig, private formBuilder: FormBuilder) { }

  ngOnInit(): void {
    this.application = this.config.data.applicationData;
    this.generateApplicationForm();
  }

  generateApplicationForm(){
    this.applicationForm = this.formBuilder.group({
      name: [{value:this.application.name, disabled:true}],
      code: [{value:this.application.code, disabled:true}],
      clientId: [{value:this.application.clientId, disabled:true}]
    });
  }

  get name(){
    return this.applicationForm.get('name');
  }
  get code(){
    return this.applicationForm.get('code');
  }
  get clientId(){
    return this.applicationForm.get('clientId');
  }
}
