import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { DynamicDialogConfig, DynamicDialogRef } from 'primeng/dynamicdialog';

@Component({
  selector: 'app-options-dialog',
  templateUrl: './options-dialog.component.html',
  styleUrls: ['./options-dialog.component.css']
})
export class OptionsDialogComponent implements OnInit {

  optionsForm: FormGroup;
  currOptions: any;

  constructor(public ref: DynamicDialogRef, public config: DynamicDialogConfig, private formBuilder: FormBuilder) { }

  ngOnInit(): void {
    this.currOptions = this.config.data.currOptions;
    this.generateOptionsForm();
  }

  generateOptionsForm(){
    this.optionsForm = this.formBuilder.group({
      type: [this.currOptions.type],
      showGrandTotals: [this.currOptions.showGrandTotals],
      showTotals: [this.currOptions.showTotals],
    });
  }

  applyOptions(){
    if(this.optionsForm.valid){
      const property = this.optionsForm.value;

      this.ref.close(property);
    }
  }

}
