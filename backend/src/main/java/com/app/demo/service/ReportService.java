package com.app.demo.service;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.app.demo.exception.AbstractGraphQLException;
import com.app.demo.model.Connections;
import com.app.demo.model.Reports;
import com.app.demo.model.SourcePaths;
import com.app.demo.payload.request.ReportRequest;
import com.app.demo.payload.response.ReportResponse;
import com.app.demo.repository.ConnectionRepository;
import com.app.demo.repository.ReportRepository;
import com.app.demo.repository.SourcePathRepository;

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
    @Autowired
    private final ConnectionRepository connectionRepository;
    @Autowired
    private final SourcePathRepository sourcePathRepository;

    @Transactional
    public Reports addReport(ReportRequest reportRequest){
        Connections connection = connectionRepository.findById(reportRequest.getConnectionId())
            .orElseThrow(() -> new AbstractGraphQLException("Connection with current id cannot be found"));

        SourcePaths sourcePath = sourcePathRepository.findById(reportRequest.getSourcePathId())
            .orElseThrow(() -> new AbstractGraphQLException("Source path with current id cannot be found"));

        String reportToken = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();

        Reports report = new Reports();
        report.setConnection(connection);
        report.setSourcePath(sourcePath);
        report.setQuery(reportRequest.getQuery());
        report.setTitle(reportRequest.getTitle());
        report.setReport(reportRequest.getReport());
        report.setReportToken(reportToken);
        report.setCreatedAt(OffsetDateTime.now());

        return reportRepository.save(report);
    }
    
    @Transactional
    public ReportResponse generateReportFromDatabase() throws IOException{

        Object[] data = getData();

        JSONObject report = new JSONObject();

        JSONObject dataSource = new JSONObject();
        dataSource.put("dataSourceType", "json");
        dataSource.put("data", data);
        report.put("dataSource", dataSource);

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
    public Reports getReport(UUID reportId){
        return reportRepository.findById(reportId)
            .orElseThrow(() -> new IllegalStateException("Report with current Id cannot be found"));
    }

    @Transactional
    public Reports saveReport(ReportRequest reportRequest){

        Reports report = new Reports();
        report.setTitle(reportRequest.getTitle());
        report.setReport(reportRequest.getReport());
        report.setCreatedAt(OffsetDateTime.now());

        return reportRepository.save(report);
    }
}
