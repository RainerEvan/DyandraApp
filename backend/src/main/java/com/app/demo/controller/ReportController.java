package com.app.demo.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.demo.model.Reports;
import com.app.demo.payload.request.ReportRequest;
import com.app.demo.payload.response.ReportResponse;
import com.app.demo.service.ReportService;
import com.app.demo.utils.ResponseHandler;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/report")
@AllArgsConstructor
public class ReportController {
    
    @Autowired
    private final ReportService reportService;

    @PostMapping(path = "/add")
    public ResponseEntity<Object> addReport(@RequestBody ReportRequest reportRequest){
        try {
            Reports report = reportService.addReport(reportRequest);
            
            return ResponseHandler.generateResponse("Report has been added successfully!", HttpStatus.OK, report);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }
    
    @GetMapping(path = "/generate/database")
    public ReportResponse generateReportFromDatabase() throws IOException{
        ReportResponse report = reportService.generateReportFromDatabase();

        return report;
    }

    @GetMapping(path = "/generate/file")
    public ReportResponse generateReportFromFile() throws IOException{
        ReportResponse report = reportService.generateReportFromFile();

        return report;
    }

    @PostMapping(path = "/save")
    public Reports saveReport(@RequestBody ReportRequest reportRequest){
        return reportService.saveReport(reportRequest);
    }
}
