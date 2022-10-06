import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { DynamicDialogConfig, DynamicDialogRef } from 'primeng/dynamicdialog';

@Component({
  selector: 'app-format-dialog',
  templateUrl: './format-dialog.component.html',
  styleUrls: ['./format-dialog.component.scss']
})
export class FormatDialogComponent implements OnInit {

  formatForm: FormGroup;
  measureFormats:any;

  constructor(public ref: DynamicDialogRef, public config: DynamicDialogConfig, private formBuilder: FormBuilder) { }

  ngOnInit(): void {
    this.measureFormats = this.config.data.measureFormats;
    this.generateFormatForm();
  }

  generateFormatForm(){
    this.formatForm = this.formBuilder.group({
      field: [''],
    });
  }

  get field(){
    return this.formatForm.get('field');
  }

  applyFormat(){
    if(this.formatForm.valid){
      const property = this.formatForm.value;

      this.ref.close(property);
    }
  }
}
