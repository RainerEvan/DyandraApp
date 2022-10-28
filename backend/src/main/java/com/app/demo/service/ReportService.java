package com.app.demo.service;

import java.io.File;
import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.sql.DataSource;
import javax.transaction.Transactional;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.app.demo.data.EMethod;
import com.app.demo.exception.AbstractGraphQLException;
import com.app.demo.model.Connections;
import com.app.demo.model.Methods;
import com.app.demo.model.Reports;
import com.app.demo.model.SourcePaths;
import com.app.demo.payload.request.ReportRequest;
import com.app.demo.repository.ConnectionRepository;
import com.app.demo.repository.ReportRepository;
import com.app.demo.repository.SourcePathRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReportService {

    @Autowired
    private final ReportRepository reportRepository;
    @Autowired
    private final ConnectionRepository connectionRepository;
    @Autowired
    private final SourcePathRepository sourcePathRepository;

    @Transactional
    public List<Reports> getAllReports(){
        return reportRepository.findAll();
    }
    
    @Transactional
    public Reports getReport(UUID reportId){
        return reportRepository.findById(reportId)
            .orElseThrow(() -> new AbstractGraphQLException("Report with current Id cannot be found"));
    }

    @Transactional
    public Reports addReport(ReportRequest reportRequest){
        Connections connection = connectionRepository.findById(reportRequest.getConnectionId())
            .orElseThrow(() -> new IllegalStateException("Connection with current id cannot be found"));

        SourcePaths sourcePath = sourcePathRepository.findById(reportRequest.getSourcePathId())
            .orElseThrow(() -> new IllegalStateException("Source path with current id cannot be found"));

        String reportJson = generateReport(connection, sourcePath, reportRequest.getQuery());
        
        String reportId = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();

        Reports report = new Reports();
        report.setConnection(connection);
        report.setSourcePath(sourcePath);
        report.setQuery(reportRequest.getQuery());
        report.setTitle(reportRequest.getTitle());
        report.setReportId(reportId);
        report.setReportConfig(reportJson);
        report.setCreatedAt(OffsetDateTime.now());

        return reportRepository.save(report);
    }

    @Transactional
    public Reports editReport(UUID reportId, ReportRequest reportRequest){
        Reports report = reportRepository.findById(reportId)
            .orElseThrow(() -> new IllegalStateException("Report with current Id cannot be found"));

        String reportJson = generateReport(report.getConnection(), report.getSourcePath(), reportRequest.getQuery());
        
        report.setQuery(reportRequest.getQuery());
        report.setTitle(reportRequest.getTitle());
        report.setReportConfig(reportJson);
        report.setUpdatedAt(OffsetDateTime.now());

        return reportRepository.save(report);
    }

    @Transactional
    public void deleteReport(UUID reportId){
        Reports report = reportRepository.findById(reportId)
            .orElseThrow(() -> new IllegalStateException("Report with current id cannot be found: "+reportId));

        reportRepository.delete(report);
    }

    @Transactional
    public String generateReport(Connections connection, SourcePaths sourcePath, String query){
        try {
            String report = "";
            Methods method = connection.getMethod();

            if(method.getName().equals(EMethod.API)){
                report = generateReportFromApi(sourcePath);
            }
            else if(method.getName().equals(EMethod.API_GATEAWAY)){
                // report = generateReportFromApi(sourcePath);
            }
            else if(method.getName().equals(EMethod.DIRECT_DB)){
                report = generateReportFromDatabase(sourcePath,query);
            }
            else if(method.getName().equals(EMethod.LOCAL_FILES)){
                report = generateReportFromFiles(sourcePath);
            }

            return report;
            
        } catch (IOException e) {
            throw new RuntimeException("Error generating report from current source! "+e.getMessage());
        }
    }
    
    @Transactional
    public String generateReportFromApi(SourcePaths sourcePath) throws IOException{
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Object[]> response = restTemplate.getForEntity(sourcePath.getPath(),Object[].class);

        Object[] data = response.getBody();

        JSONObject report = new JSONObject();

        JSONObject dataSource = new JSONObject();
        dataSource.put("dataSourceType", "json");
        dataSource.put("data", data);
        report.put("dataSource", dataSource);

        return report.toString();
    }

    @Transactional
    public String generateReportFromDatabase(SourcePaths sourcePath, String query){
        DataSource objectDataSource = new DriverManagerDataSource(
            sourcePath.getPath(),
            sourcePath.getUsername(),
            sourcePath.getPassword()
        );

        JdbcTemplate jdbcTemplate = new JdbcTemplate(objectDataSource);

        List<Map<String,Object>> data = jdbcTemplate.queryForList(query);
        
        JSONObject report = new JSONObject();

        JSONObject dataSource = new JSONObject();
        dataSource.put("dataSourceType", "json");
        dataSource.put("data", data);
        report.put("dataSource", dataSource);

        return report.toString();
    }

    @Transactional
    public String generateReportFromFiles(SourcePaths sourcePath) throws IOException{
        File file = new File(sourcePath.getPath());
        String type = FilenameUtils.getExtension(file.getName());

        JSONObject report = new JSONObject();

        JSONObject dataSource = new JSONObject();
        dataSource.put("dataSourceType", type);
        dataSource.put("filename", sourcePath.getPath());
        report.put("dataSource", dataSource);

        return report.toString();
    }

    // public ReportResponse generateReportFromFile() throws IOException{

    //     JSONObject report = new JSONObject();

    //     JSONObject dataSource = new JSONObject();
    //     dataSource.put("dataSourceType", "csv");
    //     dataSource.put("filename", "http://localhost:8081/api/files/get");

    //     JSONObject slice = new JSONObject();

    //     JSONArray rows = new JSONArray();
    //     JSONObject field = new JSONObject();
    //     field.put("uniqueName", "Category");
    //     JSONObject field2 = new JSONObject();
    //     field2.put("uniqueName", "Measures");
    //     rows.put(field);
    //     rows.put(field2);

    //     JSONArray columns = new JSONArray();
    //     JSONObject field3 = new JSONObject();
    //     field3.put("uniqueName", "Country");
    //     JSONObject field4 = new JSONObject();
    //     field4.put("uniqueName", "Color");
    //     columns.put(field3);
    //     columns.put(field4);

    //     JSONArray measures = new JSONArray();
    //     JSONObject field5 = new JSONObject();
    //     field5.put("uniqueName", "Price");
    //     measures.put(field5);

    //     slice.put("rows", rows);
    //     slice.put("columns", columns);
    //     slice.put("measures", measures);

    //     report.put("dataSource", dataSource);
    //     report.put("slice", slice);

    //     ReportResponse reportResponse = new ReportResponse(report.toString());

    //     return reportResponse;
    // }

    @Transactional
    public Reports saveReport(ReportRequest reportRequest){

        Reports report = new Reports();
        report.setTitle(reportRequest.getTitle());
        report.setCreatedAt(OffsetDateTime.now());

        return reportRepository.save(report);
    }
}
