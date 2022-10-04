import { Component, OnInit, ViewChild } from '@angular/core';
import { WebdatarocksComponent } from 'ng-webdatarocks';
import { MenuItem } from 'primeng/api';
import { DialogService, DynamicDialogRef } from 'primeng/dynamicdialog';
import { Reports } from 'src/app/models/reports';
import { ReportService } from 'src/app/services/report/report.service';
import { ExportDialogComponent } from '../dialog/export-dialog/export-dialog.component';
import { OpenDialogComponent } from '../dialog/open-dialog/open-dialog.component';
import { OptionsDialogComponent } from '../dialog/options-dialog/options-dialog.component';
import { SaveDialogComponent } from '../dialog/save-dialog/save-dialog.component';

@Component({
  selector: 'app-pivot-table',
  templateUrl: './pivot-table.component.html',
  styleUrls: ['./pivot-table.component.scss']
})
export class PivotTableComponent implements OnInit {

    @ViewChild('pivot1') child: WebdatarocksComponent;
    report:Reports;
    items: MenuItem[];

    ref: DynamicDialogRef;

    constructor(private reportService:ReportService, private dialogService:DialogService) { }

    ngOnInit(): void {
        this.generateMenubar();
    }

    ngOnDestroy(): void {
        if (this.ref) {
            this.ref.close();
        }
    }

    onReportComplete(): void {
        this.child.webDataRocks.off('reportcomplete');
        if(this.report){
            this.child.webDataRocks.setReport(JSON.parse(this.report.report));
        }
    }

    //OPEN
    showOpenDialog(){
        this.ref = this.dialogService.open(OpenDialogComponent,{
            header: 'Open Report',
            baseZIndex: 10000,
            contentStyle: {"max-height": "600px", "min-width":"50vw","overflow": "clip"},
        });

        this.ref.onClose.subscribe((reportId:any)=>{
            if(reportId){
                this.openReport(reportId);
            }
        });
    }

    openReport(reportId:any){
        this.reportService.getReport(reportId).subscribe({
            next:(response:Reports)=>{
                console.log(response.title);
                this.report = response;
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
            "report":JSON.stringify(report)
        }

        this.reportService.saveReport(reportRequest).subscribe({
            next:(response:Reports)=>{
                this.openReport(response.id);
                console.log("Report Successfully Saved: "+response.title);
            },
            error:(error:any)=>{
                console.log(error);
            }
        })
    }

    //IMPORT
    generateReportFromDatabase(){
        this.reportService.generateReportFromDatabase().subscribe({
            next:(response:Reports)=>{
                this.report = response;
                this.onReportComplete();
            },
            error:(error:any)=>{
                console.log(error);
            }
        });
    }

    generateReportFromFile(){
        this.reportService.generateReportFromFile().subscribe({
            next:(response:Reports)=>{
                this.report = response;
                this.onReportComplete();
            },
            error:(error:any)=>{
                console.log(error);
            }
        });
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

    //OPTIONS
    showOptionsDialog(){
        const options = this.child.webDataRocks.getOptions();

        this.ref = this.dialogService.open(OptionsDialogComponent,{
            header: 'Table Options',
            data:{
                currOptions: options.grid
            },
            baseZIndex: 10000,
            contentStyle: {"max-height": "650px", "min-width":"50vw","overflow": "auto"},
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
                    {
                        label: 'Open',
                        icon: 'pi pi-fw pi-folder-open',
                        command: () => {
                            this.showOpenDialog();
                        }
                    },
                    {
                        label: 'Save',
                        icon: 'pi pi-fw pi-save',
                        command: () => {
                            this.showSaveDialog();
                        }
                    },
                    {
                        label: 'Import', 
                        icon: 'pi pi-fw pi-database',
                        items: [
                            {
                                label: 'File',
                                command: () => {
                                    this.generateReportFromFile();
                                }
                            },
                            {
                                label: 'Database',
                                command: () => {
                                    this.generateReportFromDatabase();
                                }
                            },
                        ]
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
                        icon: 'pi pi-fw pi-sliders-h'
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

    // report = {
    //     "dataSource": {
    //         "dataSourceType": "csv",
    //         "filename": "https://cdn.webdatarocks.com/data/data.csv"
    //     },
    //     "slice": {
    //         "rows": [
    //             {
    //                 "uniqueName": "Destination",
    //                 // "sort": "asc"
    //             },
    //             {
    //                 "uniqueName": "Color",
    //                 // "sort": "asc"
    //             },
    //             {
    //                 "uniqueName": "Measures"
    //             }
    //         ],
    //         "columns": [
    //             {
    //                 "uniqueName": "Category",
    //                 // "sort": "asc"
    //             },
    //             {
    //                 "uniqueName": "Country",
    //                 // "sort": "asc"
    //             }
    //         ],
    //         "measures": [
    //             {
    //                 "uniqueName": "Price",
    //                 "aggregation": "sum"
    //             },
    //             {
    //                 "uniqueName": "Discount",
    //                 "aggregation": "sum"
    //             },
    //             {
    //                 "uniqueName": "Quantity",
    //                 "aggregation": "sum"
    //             }
    //         ],
    //         // "expands": {
    //         //     "expandAll": false,
    //         //     "rows": [
    //         //         {
    //         //             "tuple": [
    //         //                 "Destination.France"
    //         //             ]
    //         //         }
    //         //     ],
    //         //     "columns": [
    //         //         {
    //         //             "tuple": [
    //         //                 "Category.Accessories"
    //         //             ]
    //         //         }
    //         //     ]
    //         // },
    //         // "drills": {
    //         //     "drillAll": true
    //         // }
    //     }
    //     // "options": {
    //     //     "grid": {
    //     //         "type": "compact",
    //     //         "title": "",
    //     //         "showFilter": true,
    //     //         "showHeaders": true,
    //     //         "showTotals": true,
    //     //         "showGrandTotals": "on",
    //     //         "showHierarchies": true,
    //     //         "showHierarchyCaptions": true,
    //     //         "showReportFiltersArea": true
    //     //     },
    //     //     "configuratorActive": false,
    //     //     "configuratorButton": true,
    //     //     "showAggregations": true,
    //     //     "showCalculatedValuesButton": true,
    //     //     "drillThrough": true,
    //     //     "showDrillThroughConfigurator": true,
    //     //     "sorting": "on",
    //     //     "datePattern": "dd/MM/yyyy",
    //     //     "dateTimePattern": "dd/MM/yyyy HH:mm:ss",
    //     //     "saveAllFormats": false,
    //     //     "showDefaultSlice": true,
    //     //     "defaultHierarchySortName": "asc"
    //     // },
    //     // "formats": [
    //     //     {
    //     //         "name": "",
    //     //         "thousandsSeparator": ",",
    //     //         "decimalSeparator": ".",
    //     //         "decimalPlaces": 2,
    //     //         "maxSymbols": 20,
    //     //         "currencySymbol": "",
    //     //         "currencySymbolAlign": "left",
    //     //         "nullValue": " ",
    //     //         "infinityValue": "Infinity",
    //     //         "divideByZeroValue": "Infinity"
    //     //     }
    //     // ]
    // }
}
