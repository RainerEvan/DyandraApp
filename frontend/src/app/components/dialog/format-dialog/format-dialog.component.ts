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
  selectedFieldValue:any;
  isFormEnabled:boolean;

  textAlignOpt:any;
  thousandsSeparatorOpt:any;
  decimalSeparatorOpt:any;
  decimalPlacesOpt:any;
  currencySymbolAlignOpt:any;
  isPercentOpt:any;

  constructor(public ref: DynamicDialogRef, public config: DynamicDialogConfig, private formBuilder: FormBuilder) {
    this.textAlignOpt = [
      {label:'Left',value:'left'},
      {label:'Right',value:'right'}
    ]
    this.thousandsSeparatorOpt = [
      {label:'None',value:''},
      {label:'Space',value:' '},
      {label:'Dot',value:'.'},
      {label:'Comma',value:','},
    ]
    this.decimalSeparatorOpt = [
      {label:'Dot',value:'.'},
      {label:'Comma',value:','},
    ]
    this.decimalPlacesOpt = [
      {label:'None',value:-1},
      {label:'1',value:1},
      {label:'2',value:2},
      {label:'3',value:3},
      {label:'4',value:4},
      {label:'5',value:5},
      {label:'6',value:6},
      {label:'7',value:7},
      {label:'8',value:8},
      {label:'9',value:9},
    ]
    this.currencySymbolAlignOpt = [
      {label:'Left',value:'left'},
      {label:'Right',value:'right'}
    ]
    this.isPercentOpt = [
      {label:'Yes',value:true},
      {label:'No',value:false}
    ]
  }

  ngOnInit(): void {
    this.measureFormats = this.config.data.measureFormats;
    this.generateFormatForm();
  }

  toggleForm(){
    if(this.selectedFieldValue){
      this.isFormEnabled = true;

      this.formatForm.enable();
      this.formatForm.controls['textAlign'].setValue(this.selectedFieldValue.format.textAlign);
      this.formatForm.controls['thousandsSeparator'].setValue(this.selectedFieldValue.format.thousandsSeparator);
      this.formatForm.controls['decimalSeparator'].setValue(this.selectedFieldValue.format.decimalSeparator);
      this.formatForm.controls['decimalPlaces'].setValue(this.selectedFieldValue.format.decimalPlaces);
      this.formatForm.controls['currencySymbol'].setValue(this.selectedFieldValue.format.currencySymbol);
      this.formatForm.controls['currencySymbolAlign'].setValue(this.selectedFieldValue.format.currencySymbolAlign);
      this.formatForm.controls['nullValue'].setValue(this.selectedFieldValue.format.nullValue);
      this.formatForm.controls['isPercent'].setValue(this.selectedFieldValue.format.isPercent);
    }
  }

  generateFormatForm(){
    this.isFormEnabled = false;

    this.formatForm = this.formBuilder.group({
      textAlign: [{value:null, disabled:true}],
      thousandsSeparator: [{value:null, disabled:true}],
      decimalSeparator: [{value:null, disabled:true}],
      decimalPlaces: [{value:null, disabled:true}],
      currencySymbol: [{value:null, disabled:true}],
      currencySymbolAlign: [{value:null, disabled:true}],
      nullValue: [{value:null, disabled:true}],
      isPercent: [{value:null, disabled:true}],
    });
  }

  get textAlign(){
    return this.formatForm.get('textAlign');
  }
  get thousandsSeparator(){
    return this.formatForm.get('thousandsSeparator');
  }
  get decimalSeparator(){
    return this.formatForm.get('decimalSeparator');
  }
  get decimalPlaces(){
    return this.formatForm.get('decimalPlaces');
  }
  get currencySymbol(){
    return this.formatForm.get('currencySymbol');
  }
  get currencySymbolAlign(){
    return this.formatForm.get('currencySymbolAlign');
  }
  get nullValue(){
    return this.formatForm.get('nullValue');
  }
  get isPercent(){
    return this.formatForm.get('isPercent');
  }

  applyFormat(){
    if(this.formatForm.valid){
      const property = {
        name: this.selectedFieldValue.uniqueName,
        format: this.formatForm.value
      }

      this.ref.close(property);
    }
  }
}
