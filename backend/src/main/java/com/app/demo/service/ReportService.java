package com.app.demo.service;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.app.demo.payload.ReportResponse;

@Service
public class ReportService {

    @Value("${api.orderApiUrl}")
    private String orderApiUrl;
    
    public ReportResponse generateReport() throws IOException{

        Object[] data = test();

        JSONObject report = new JSONObject();

        JSONObject dataSource = new JSONObject();
        dataSource.put("dataSourceType", "csv");
        // dataSource.put("filename", "https://cdn.webdatarocks.com/data/data.csv");
        dataSource.put("data", data);

        JSONObject slice = new JSONObject();

        JSONArray rows = new JSONArray();
        JSONObject field = new JSONObject();
        field.put("uniqueName", "country");
        JSONObject field2 = new JSONObject();
        field2.put("uniqueName", "Measures");
        rows.put(field);
        rows.put(field2);

        JSONArray columns = new JSONArray();
        JSONObject field3 = new JSONObject();
        field3.put("uniqueName", "category");
        JSONObject field4 = new JSONObject();
        field4.put("uniqueName", "color");
        columns.put(field3);
        columns.put(field4);

        JSONArray measures = new JSONArray();
        JSONObject field5 = new JSONObject();
        field5.put("uniqueName", "price");
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

    public Object[] test(){
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Object[]> response = restTemplate.getForEntity(orderApiUrl,Object[].class);

        return response.getBody();
    }
}
