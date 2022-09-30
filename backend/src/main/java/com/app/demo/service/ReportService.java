package com.app.demo.service;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.app.demo.model.Reports;
import com.app.demo.payload.ReportResponse;
import com.app.demo.repository.ReportRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReportService {

    @Value("${api.orderApiUrl}")
    private String orderApiUrl;
    @Value("${api.fileApiUrl}")
    private String fileApiUrl;

    @Autowired
    private final ReportRepository reportRepository;
    
    public ReportResponse generateReportFromDatabase() throws IOException{

        Object[] data = getData();

        JSONObject report = new JSONObject();

        JSONObject dataSource = new JSONObject();
        dataSource.put("dataSourceType", "json");
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

        ReportResponse reportResponse = new ReportResponse(report.toString());

        return reportResponse;
    }

    public ReportResponse generateReportFromFile() throws IOException{

        JSONObject report = new JSONObject();

        JSONObject dataSource = new JSONObject();
        dataSource.put("dataSourceType", "csv");
        dataSource.put("filename", "http://localhost:8081/api/files/get");

        JSONObject slice = new JSONObject();

        JSONArray rows = new JSONArray();
        JSONObject field = new JSONObject();
        field.put("uniqueName", "Category");
        JSONObject field2 = new JSONObject();
        field2.put("uniqueName", "Measures");
        rows.put(field);
        rows.put(field2);

        JSONArray columns = new JSONArray();
        JSONObject field3 = new JSONObject();
        field3.put("uniqueName", "Country");
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

        ReportResponse reportResponse = new ReportResponse(report.toString());

        return reportResponse;
    }

    public Object[] getData(){
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Object[]> response = restTemplate.getForEntity(orderApiUrl+"/all",Object[].class);

        return response.getBody();
    }

    @Transactional
    public List<Reports> getAllReports(){
        return reportRepository.findAll();
    }

    @Transactional
    public Reports saveReport(String title, String reportJson){

        Reports report = new Reports();
        report.setApplication("App");
        report.setTitle(title);
        report.setReport(reportJson);
        report.setCreatedAt(OffsetDateTime.now());

        return reportRepository.save(report);
    }
}
