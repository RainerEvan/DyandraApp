package com.app.demo.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.demo.model.Reports;
import com.app.demo.payload.ReportResponse;
import com.app.demo.service.ReportService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/report")
@AllArgsConstructor
public class ReportController {
    
    @Autowired
    private final ReportService reportService;
    
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

    @GetMapping(path = "/all")
    public List<Reports> getAllReports(){
        return reportService.getAllReports();
    }

    @PostMapping(path = "/save")
    public Reports saveReport(@RequestBody Object reportJson){
        return reportService.saveReport("Contoh Report", reportJson.toString());
    }
}
