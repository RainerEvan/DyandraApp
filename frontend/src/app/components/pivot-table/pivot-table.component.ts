import { Component, OnInit, ViewChild } from '@angular/core';
import { WebdatarocksComponent } from 'ng-webdatarocks';
import { MenuItem } from 'primeng/api';
import { DialogService, DynamicDialogRef } from 'primeng/dynamicdialog';
import { Reports } from 'src/app/models/reports';
import { ReportService } from 'src/app/services/report/report.service';
import { ExportDialogComponent } from '../dialog/export-dialog/export-dialog.component';
import { OpenDialogComponent } from '../dialog/open-dialog/open-dialog.component';

@Component({
  selector: 'app-pivot-table',
  templateUrl: './pivot-table.component.html',
  styleUrls: ['./pivot-table.component.scss']
})
export class PivotTableComponent implements OnInit {

    @ViewChild('pivot1') child: WebdatarocksComponent;
    report:any;
    isReportSaved:boolean = false;
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
        this.child.webDataRocks.setReport(this.report);
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

            }
        });
    }

    //SAVE
    saveReport(){
        const reportJson = this.child.webDataRocks.getReport();

        this.reportService.saveReport(reportJson).subscribe({
            next:(response:any)=>{
                this.isReportSaved = true;
                console.log(response);
                setTimeout(() => this.isReportSaved = false, 3000);
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
                this.report = JSON.parse(response.report);
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
                this.report = JSON.parse(response.report);
                this.onReportComplete();
            },
            error:(error:any)=>{
                console.log(error);
            }
        });
    }

    //EXPORT
    exportReport(format:any ,property:any){
        this.child.webDataRocks.exportTo(
            format,
            property,
            () => console.log("Export Success")
        );
    }

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
                        icon: 'pi pi-fw pi-save'
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
                        icon: 'pi pi-fw pi-cog'
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
