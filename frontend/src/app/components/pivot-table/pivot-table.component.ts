import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-pivot-table',
  templateUrl: './pivot-table.component.html',
  styleUrls: ['./pivot-table.component.scss']
})
export class PivotTableComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }

  report = {
    "dataSource": {
        "dataSourceType": "csv",
        "filename": "https://cdn.webdatarocks.com/data/data.csv"
    },
    "slice": {
        "rows": [
            {
                "uniqueName": "Destination",
                "sort": "asc"
            },
            {
                "uniqueName": "Color",
                "sort": "asc"
            },
            {
                "uniqueName": "Measures"
            }
        ],
        "columns": [
            {
                "uniqueName": "Category",
                "sort": "asc"
            },
            {
                "uniqueName": "Country",
                "sort": "asc"
            }
        ],
        "measures": [
            {
                "uniqueName": "Price",
                "aggregation": "sum"
            },
            {
                "uniqueName": "Discount",
                "aggregation": "sum"
            },
            {
                "uniqueName": "Quantity",
                "aggregation": "sum"
            }
        ],
        "expands": {
            "expandAll": false,
            "rows": [
                {
                    "tuple": [
                        "Destination.France"
                    ]
                }
            ],
            "columns": [
                {
                    "tuple": [
                        "Category.Accessories"
                    ]
                }
            ]
        },
        "drills": {
            "drillAll": false
        }
    },
    "options": {
        "grid": {
            "type": "compact",
            "title": "",
            "showFilter": true,
            "showHeaders": true,
            "showTotals": true,
            "showGrandTotals": "on",
            "showHierarchies": true,
            "showHierarchyCaptions": true,
            "showReportFiltersArea": true
        },
        "configuratorActive": false,
        "configuratorButton": true,
        "showAggregations": true,
        "showCalculatedValuesButton": true,
        "drillThrough": true,
        "showDrillThroughConfigurator": true,
        "sorting": "on",
        "datePattern": "dd/MM/yyyy",
        "dateTimePattern": "dd/MM/yyyy HH:mm:ss",
        "saveAllFormats": false,
        "showDefaultSlice": true,
        "defaultHierarchySortName": "asc"
    },
    "formats": [
        {
            "name": "",
            "thousandsSeparator": " ",
            "decimalSeparator": ".",
            "decimalPlaces": 2,
            "maxSymbols": 20,
            "currencySymbol": "",
            "currencySymbolAlign": "left",
            "nullValue": " ",
            "infinityValue": "Infinity",
            "divideByZeroValue": "Infinity"
        }
    ]
    }
}
