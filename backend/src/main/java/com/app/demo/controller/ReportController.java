package com.app.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.demo.payload.ReportResponse;
import com.app.demo.service.ReportService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/report")
@AllArgsConstructor
public class ReportController {
    
    @Autowired
    private final ReportService reportService;
    
    @GetMapping(path = "/generate")
    public ReportResponse generateReport(){
        ReportResponse report = reportService.generateReport();

        return report;
    }
}
