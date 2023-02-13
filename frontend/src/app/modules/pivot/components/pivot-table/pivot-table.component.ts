import { Component, OnInit, ViewChild } from '@angular/core';
import { OptionsDialogComponent } from '../options-dialog/options-dialog.component';
import { FormatDialogComponent } from '../format-dialog/format-dialog.component';
import { ExportDialogComponent } from '../export-dialog/export-dialog.component';
import { SaveDialogComponent } from '../save-dialog/save-dialog.component';
import { ReportService } from 'src/app/services/report/report.service';
import { DialogService, DynamicDialogRef } from 'primeng/dynamicdialog';
import { ActivatedRoute } from '@angular/router';
import * as Highcharts from "highcharts";
import "webdatarocks/webdatarocks.highcharts.js";
import { WebdatarocksComponent } from 'ng-webdatarocks';
import { Reports } from 'src/app/models/reports';
import { MenuItem } from 'primeng/api';
import { PivotService } from 'src/app/services/pivot/pivot.service';

@Component({
  selector: 'app-pivot-table',
  templateUrl: './pivot-table.component.html',
  styleUrls: ['./pivot-table.component.css']
})
export class PivotTableComponent implements OnInit {

  @ViewChild('pivot1') child: WebdatarocksComponent;
  Highcharts: typeof Highcharts = Highcharts;

  report: Reports;
  items: MenuItem[];
  reportConfig: any;
  token: string;
  ref: DynamicDialogRef;
  chartType:any;
  selectedChart:any;

  constructor(private reportService:ReportService, private pivotService:PivotService, private dialogService:DialogService, private route:ActivatedRoute) {
      this.chartType = [
          {name:"Area Spline", value:"areaspline"},
          {name:"Basic Area", value:"area"},
          {name:"Basic Bar", value:"bar"},
          {name:"Basic Column", value:"column"},
          {name:"Line Chart", value:"line"},
          {name:"Pie Chart", value:"pie"},
          {name:"Spline", value:"spline"},
      ]
  }

  ngOnInit(): void {
      this.generateMenubar();
      this.token = this.route.snapshot.paramMap.get('token');
      if(this.token){
          this.openReport(this.token);
      }
  }

  ngOnDestroy(): void {
      if (this.ref) {
          this.ref.close();
      }
  }

  onReportComplete(): void {
      this.child.webDataRocks.off('reportcomplete');
      if(this.report){
          this.child.webDataRocks.setReport(JSON.parse(this.reportConfig));
      }
  }

  createChart(){
      if(this.selectedChart){
          this.child.webDataRocks.highcharts.getData(
              {
                  type:this.selectedChart,
              },
              data => {
                  this.Highcharts.chart("highCharts",data);
              },
              data => {
                  this.Highcharts.chart("highCharts",data);
              }
          );
      }
  }

  //OPEN
  // showOpenDialog(){
  //     this.ref = this.dialogService.open(OpenDialogComponent,{
  //         header: 'Open Report',
  //         baseZIndex: 10000,
  //         contentStyle: {"max-height": "600px", "width":"55vw","min-width":"450px", "max-width":"700px","overflow": "auto"},
  //     });

  //     this.ref.onClose.subscribe((reportId:any)=>{
  //         if(reportId){
  //             this.openReport(reportId);
  //         }
  //     });
  // }

  openReport(token:any){
      this.reportService.getReportByToken(token).subscribe({
          next:(response:Reports)=>{
              this.report = response;
              this.generateReport(response.id);
          },
          error:(error:any)=>{
              console.log(error);
          }
      });
  }

  generateReport(reportId:any){
      this.pivotService.generateReport(reportId).subscribe({
          next:(response:any)=>{
              this.reportConfig = response.data;
              this.onReportComplete();
          },
          error:(error:any)=>{
              console.log(error);
          }
      });
  }

  //SAVE
  showSaveDialog(){
      if(this.report){
          this.ref = this.dialogService.open(SaveDialogComponent,{
              header: 'Save Report',
              data:{
                  reportTitle:this.report.title
              },
              baseZIndex: 10000,
              contentStyle: {"max-height": "650px", "min-width":"30vw","overflow": "auto"},
          });
  
          this.ref.onClose.subscribe((title:any)=>{
              if(title){
                  this.saveReport(title);
              }
          });
      }
  }

  saveReport(title:string){
      const report = this.child.webDataRocks.getReport();

      const reportRequest = {
          title,
          "reportConfig":JSON.stringify(report)
      }

      this.pivotService.saveReport(this.report.id, reportRequest).subscribe({
          next:(response:any)=>{
              console.log(response);
          },
          error:(error:any)=>{
              console.log(error);
          }
      })
  }

  //EXPORT
  showExportDialog(format:string){
      this.ref = this.dialogService.open(ExportDialogComponent,{
          header: 'Download as '+format.toUpperCase(),
          data: {
              format: format,
          },
          baseZIndex: 10000,
          contentStyle: {"max-height": "650px", "min-width":"30vw","overflow": "auto"},
      });

      this.ref.onClose.subscribe((property:any)=>{
          if(property){
              this.exportReport(format,property);
          }
      });
  }

  exportReport(format:any ,property:any){
      this.child.webDataRocks.exportTo(
          format,
          property,
          () => console.log("Export Success")
      );
  }

  //FORMAT
  showFormatDialog(){
      const measures = this.child.webDataRocks.getAllMeasures();
      const measureFormats = [];

      if(measures.length > 0){
          measureFormats.push({
              uniqueName: "",
              caption: "All values",
              format: this.child.webDataRocks.getFormat(""),
          });
          
          measures.forEach((measure)=>{
              var measureFormat = {
                  uniqueName: measure.uniqueName,
                  caption: measure.caption,
                  format: this.child.webDataRocks.getFormat(measure.uniqueName)
              }
  
              measureFormats.push(measureFormat);
          });
      }

      this.ref = this.dialogService.open(FormatDialogComponent,{
          header: 'Format Cells',
          data:{
              measureFormats: measureFormats,
          },
          baseZIndex: 10000,
          contentStyle: {"max-height": "650px", "width":"50vw","min-width":"500px", "max-width":"600px", "overflow": "auto"},
      });

      this.ref.onClose.subscribe((property:any)=>{
          if(property){
              this.applyFormat(property);
          }
      });
  }

  applyFormat(property:any){
      this.child.webDataRocks.setFormat(property.format, property.name);
      this.child.webDataRocks.refresh();
  }

  //OPTIONS
  showOptionsDialog(){
      const options = this.child.webDataRocks.getOptions();

      this.ref = this.dialogService.open(OptionsDialogComponent,{
          header: 'Layout Options',
          data:{
              currOptions: options.grid
          },
          baseZIndex: 10000,
          contentStyle: {"max-height": "650px", "width":"50vw","min-width":"400px", "max-width":"600px", "overflow": "auto"},
      });

      this.ref.onClose.subscribe((property:any)=>{
          if(property){
              this.applyOptions(property);
          }
      });
  }

  applyOptions(property:any){
      this.child.webDataRocks.setOptions({
          grid: property
      });
      this.child.webDataRocks.refresh();
  }

  //MENUBAR
  generateMenubar(){
      this.items = [
          {
              label: 'Report',
              items: [
                  // {
                  //     label: 'Open',
                  //     icon: 'pi pi-fw pi-folder-open',
                  //     command: () => {
                  //         this.showOpenDialog();
                  //     }
                  // },
                  {
                      label: 'Save',
                      icon: 'pi pi-fw pi-save',
                      command: () => {
                          this.showSaveDialog();
                      }
                  },
                  {
                      label: 'Export', 
                      icon: 'pi pi-fw pi-file',
                      items: [
                          {
                              label: 'Download as PDF',
                              command: () => {
                                  this.showExportDialog('pdf');
                              }
                          },
                          {
                              label: 'Download as Excel',
                              command: () => {
                                  this.showExportDialog('excel');
                              }
                          },
                      ]
                  },
              ]
          },
          {
              label: 'Edit',
              items: [
                  {
                      label: 'Format', 
                      icon: 'pi pi-fw pi-sliders-h',
                      command: () => {
                          this.showFormatDialog();
                      }
                  },
                  {
                      label: 'Options', 
                      icon: 'pi pi-fw pi-cog',
                      command: () => {
                          this.showOptionsDialog();
                      }
                  }
              ]
          }
      ];
  }


}
