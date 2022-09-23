import { Component, OnInit, ViewChild } from '@angular/core';
import { WebdatarocksComponent } from 'ng-webdatarocks';
import { Report } from 'src/app/models/report';
import { ReportService } from 'src/app/services/report/report.service';

@Component({
  selector: 'app-pivot-table',
  templateUrl: './pivot-table.component.html',
  styleUrls: ['./pivot-table.component.scss']
})
export class PivotTableComponent implements OnInit {

    report:any;
    isReportSaved:boolean = false;

    constructor(private reportService:ReportService) { }

    ngOnInit(): void {
    }

    @ViewChild('pivot1') child: WebdatarocksComponent;

    onReportComplete(): void {
        this.child.webDataRocks.off('reportcomplete');
        this.child.webDataRocks.setReport(this.report);
    }

    generateReportFromDatabase(){
        this.reportService.generateReportFromDatabase().subscribe({
            next:(response:Report)=>{
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
            next:(response:Report)=>{
                this.report = JSON.parse(response.report);
                this.onReportComplete();
            },
            error:(error:any)=>{
                console.log(error);
            }
        });
    }

    saveReport(){
        var reportJson = this.child.webDataRocks.getReport();

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
