import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { DynamicDialogConfig, DynamicDialogRef } from 'primeng/dynamicdialog';

@Component({
  selector: 'app-export-dialog',
  templateUrl: './export-dialog.component.html',
  styleUrls: ['./export-dialog.component.css']
})
export class ExportDialogComponent implements OnInit {

  exportForm: FormGroup;
  format:string;

  constructor(public ref: DynamicDialogRef, public config: DynamicDialogConfig, private formBuilder: FormBuilder) { }

  ngOnInit(): void {
    this.format = this.config.data.format;
    this.generateExportForm();
  }

  generateExportForm(){
    this.exportForm = this.formBuilder.group({
      filename: [null, [Validators.required,Validators.maxLength(255)]],
      destinationType: ['file'],
      pageOrientation: ['portrait'],
    });
  }

  exportReport(){
    if(this.exportForm.valid){
      const property = this.exportForm.value;

      this.ref.close(property);
    }
  }

}
