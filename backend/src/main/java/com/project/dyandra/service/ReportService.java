package com.project.dyandra.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.sql.DataSource;
import javax.transaction.Transactional;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.json.CDL;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.project.dyandra.data.EMethod;
import com.project.dyandra.exception.AbstractGraphQLException;
import com.project.dyandra.model.Applications;
import com.project.dyandra.model.Connections;
import com.project.dyandra.model.Methods;
import com.project.dyandra.model.Reports;
import com.project.dyandra.model.SourcePaths;
import com.project.dyandra.payload.request.ReportRequest;
import com.project.dyandra.payload.response.ReportTemplateResponse;
import com.project.dyandra.repository.ApplicationRepository;
import com.project.dyandra.repository.ConnectionRepository;
import com.project.dyandra.repository.ReportRepository;
import com.project.dyandra.repository.SourcePathRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReportService{

    @Value("#{${sqlCommandsExclusion}}")
    private List<String> sqlCommandsExclusion;
    @Value("${dyandraClientUrl}")
    private String dyandraClientUrl;

    @Autowired
    private final ReportRepository reportRepository;
    @Autowired
    private final ApplicationRepository applicationRepository;
    @Autowired
    private final ConnectionRepository connectionRepository;
    @Autowired
    private final SourcePathRepository sourcePathRepository;

    @Transactional
    public List<Reports> getAllReports(){
        return reportRepository.findAll();
    }
    
    @Transactional
    public Reports getReportByReportId(String reportId){
        return reportRepository.findByReportId(reportId)
            .orElseThrow(() -> new AbstractGraphQLException("Report with current id cannot be found"));
    }

    @Transactional
    public Reports addReport(ReportRequest reportRequest){
        Connections connection = connectionRepository.findById(reportRequest.getConnectionId())
            .orElseThrow(() -> new IllegalStateException("Connection with current id cannot be found"));

        SourcePaths sourcePath = sourcePathRepository.findById(reportRequest.getSourcePathId())
            .orElseThrow(() -> new IllegalStateException("Source path with current id cannot be found"));
        
        String query = reportRequest.getQuery();
        
        if(query != null){
            validateQuery(query);
        }

        String reportId = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();

        Reports report = new Reports();
        report.setConnection(connection);
        report.setSourcePath(sourcePath);
        report.setQuery(query);
        report.setTitle(reportRequest.getTitle());
        report.setReportId(reportId);
        report.setReportConfig("{}");
        report.setCreatedAt(OffsetDateTime.now());

        return reportRepository.save(report);
    }

    @Transactional
    public Reports editReport(UUID reportId, ReportRequest reportRequest){
        Reports report = reportRepository.findById(reportId)
            .orElseThrow(() -> new IllegalStateException("Report with current id cannot be found"));

        String query = reportRequest.getQuery();
        
        if(query != null){
            validateQuery(query);
        }

        report.setTitle(reportRequest.getTitle());
        report.setQuery(query);
        report.setUpdatedAt(OffsetDateTime.now());

        return reportRepository.save(report);
    }

    @Transactional
    public Reports saveReport(UUID reportId, ReportRequest reportRequest){
        Reports report = reportRepository.findById(reportId)
            .orElseThrow(() -> new IllegalStateException("Report with current id cannot be found"));

        JSONObject reportConfig = new JSONObject(reportRequest.getReportConfig());
        reportConfig.remove("dataSource");

        report.setTitle(reportRequest.getTitle());
        report.setReportConfig(reportConfig.toString());
        report.setUpdatedAt(OffsetDateTime.now());

        return reportRepository.save(report);
    }

    @Transactional
    public void deleteReport(UUID reportId){
        Reports report = reportRepository.findById(reportId)
            .orElseThrow(() -> new IllegalStateException("Report with current id cannot be found"));

        reportRepository.delete(report);
    }

    @Transactional
    public void validateQuery(String query){
        sqlCommandsExclusion.stream().forEach(
            (command) -> {
                if(query.toUpperCase().contains(command)){
                    throw new IllegalStateException(String.format("Query cannot contain %s command",command));
                }
            }
        );
    }

    @Transactional
    public String generateReport(UUID reportId){
        Reports report = reportRepository.findById(reportId)
            .orElseThrow(() -> new IllegalStateException("Report with current report id cannot be found"));

        JSONObject reportJson = new JSONObject(report.getReportConfig());
        
        JSONObject dataSource = generateReportData(report.getConnection(), report.getSourcePath(), report.getQuery());

        reportJson.put("dataSource",dataSource);

        return reportJson.toString();
    }

    @Transactional
    public JSONObject generateReportData(Connections connection, SourcePaths sourcePath, String query){
        try {
            JSONObject dataSource = new JSONObject();
            Methods method = connection.getMethod();

            if(method.getName().equals(EMethod.API)){
                dataSource = generateReportFromApi(sourcePath);
            }
            else if(method.getName().equals(EMethod.API_GATEAWAY)){
                // report = generateReportFromApi(sourcePath);
            }
            else if(method.getName().equals(EMethod.DIRECT_DB)){
                dataSource = generateReportFromDatabase(sourcePath,query);
            }
            else if(method.getName().equals(EMethod.LOCAL_FILES)){
                dataSource = generateReportFromFiles(sourcePath);
            }

            return dataSource;
            
        } catch (IOException e) {
            throw new RuntimeException("Error generating report from current source! "+e.getMessage());
        }
    }
    
    @Transactional
    public JSONObject generateReportFromApi(SourcePaths sourcePath) throws IOException{
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Object[]> response = restTemplate.getForEntity(sourcePath.getPath(),Object[].class);

        Object[] data = response.getBody();

        JSONObject dataSource = new JSONObject();
        dataSource.put("dataSourceType", "json");
        dataSource.put("data", data);

        return dataSource;
    }

    @Transactional
    public JSONObject generateReportFromDatabase(SourcePaths sourcePath, String query){
        DataSource objectDataSource = new DriverManagerDataSource(
            sourcePath.getPath(),
            sourcePath.getUsername(),
            sourcePath.getPassword()
        );

        JdbcTemplate jdbcTemplate = new JdbcTemplate(objectDataSource);

        List<Map<String,Object>> data = jdbcTemplate.queryForList(query);
        
        JSONObject dataSource = new JSONObject();
        dataSource.put("dataSourceType", "json");
        dataSource.put("data", data);

        return dataSource;
    }

    @Transactional
    public JSONObject generateReportFromFiles(SourcePaths sourcePath) throws IOException{
        File file = new File(sourcePath.getPath());
        String type = FilenameUtils.getExtension(file.getName());

        if(type.equals("json")){
            String dataString = FileUtils.readFileToString(file,"UTF-8");
            JSONArray data = new JSONArray(dataString);

            JSONObject dataSource = new JSONObject();
            dataSource.put("dataSourceType", type);
            dataSource.put("data", data);

            return dataSource;
        }
        else if(type.equals("csv")){
            InputStream is = new FileInputStream(file);
            String dataString = new BufferedReader(new InputStreamReader(Objects.requireNonNull(is), StandardCharsets.UTF_8))
                                .lines()
                                .collect(Collectors.joining("\n"));
            JSONArray data = CDL.toJSONArray(dataString);

            JSONObject dataSource = new JSONObject();
            dataSource.put("dataSourceType", type);
            dataSource.put("data", data);

            return dataSource;
        }

        return null;
    }

    @Transactional
    public ReportTemplateResponse getTemplate(String reportId){
        Reports report = reportRepository.findByReportId(reportId)
            .orElseThrow(() -> new IllegalStateException("Report with current id cannot be found"));

        String template = "<iframe width=\"100%\" height=\"750\" src=\"{clientUrl}/#/pivot/{reportId}\"></iframe>";
        template = template.replace("{clientUrl}", dyandraClientUrl).replace("{reportId}", reportId);

        return new ReportTemplateResponse(report.getTitle(), template);
    }

    @Transactional
    public boolean verifyClientReport(String clientId, String reportId){
        try{
            Reports report = reportRepository.findByReportId(reportId)
                .orElseThrow(() -> new IllegalStateException("Report with current id cannot be found"));

            Applications application = applicationRepository.findByClientId(clientId)
                .orElseThrow(() -> new IllegalStateException("Application with current id cannot be found"));

            if(application.getClientId().equals(report.getConnection().getApplication().getClientId())){
                return true;
            }
            throw new IllegalStateException("Invalid credential to access report");
        } catch(Exception e){
            throw new IllegalStateException(e.getMessage());
        }
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
}
