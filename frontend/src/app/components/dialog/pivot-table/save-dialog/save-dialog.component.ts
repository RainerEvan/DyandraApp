import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { DynamicDialogConfig, DynamicDialogRef } from 'primeng/dynamicdialog';

@Component({
  selector: 'app-save-dialog',
  templateUrl: './save-dialog.component.html',
  styleUrls: ['./save-dialog.component.scss']
})
export class SaveDialogComponent implements OnInit {

  saveForm: FormGroup;
  reportTitle: string;

  constructor(public ref: DynamicDialogRef, private config:DynamicDialogConfig, private formBuilder: FormBuilder) { }

  ngOnInit(): void {
    this.reportTitle = this.config.data.reportTitle;
    this.generateSaveForm();
  }

  generateSaveForm(){
    this.saveForm = this.formBuilder.group({
      title: [this.reportTitle, [Validators.required,Validators.maxLength(255)]],
    });
  }

  get title(){
    return this.saveForm.get('title');
  }

  saveReport(){
    if(this.saveForm.valid){
      const title = this.saveForm.value.title;

      this.ref.close(title);
    }
  }

}
