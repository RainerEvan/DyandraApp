package com.app.demo.service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.app.demo.payload.ReportResponse;

@Service
public class ReportService {
    
    public ReportResponse generateReport(){
        JSONObject report = new JSONObject();

        JSONObject dataSource = new JSONObject();
        dataSource.put("dataSourceType", "csv");
        dataSource.put("filename", "https://cdn.webdatarocks.com/data/data.csv");

        JSONObject slice = new JSONObject();

        JSONArray rows = new JSONArray();
        JSONObject field = new JSONObject();
        field.put("uniqueName", "Destination");
        JSONObject field2 = new JSONObject();
        field2.put("uniqueName", "Measures");
        rows.put(field);
        rows.put(field2);

        JSONArray columns = new JSONArray();
        JSONObject field3 = new JSONObject();
        field3.put("uniqueName", "Category");
        JSONObject field4 = new JSONObject();
        field4.put("uniqueName", "Color");
        columns.put(field3);
        columns.put(field4);

        JSONArray measures = new JSONArray();
        JSONObject field5 = new JSONObject();
        field5.put("uniqueName", "Price");
        measures.put(field5);

        slice.put("rows", rows);
        slice.put("columns", columns);
        slice.put("measures", measures);

        report.put("dataSource", dataSource);
        report.put("slice", slice);

        boolean isToolbar = true;
        ReportResponse reportResponse = new ReportResponse(report.toString(), isToolbar);

        return reportResponse;
    }
}
