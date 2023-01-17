package com.project.dyandra.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.dyandra.model.Reports;
import com.project.dyandra.payload.request.ReportRequest;
import com.project.dyandra.service.ReportService;
import com.project.dyandra.utils.ResponseHandler;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/public/report")
@AllArgsConstructor
public class PublicController {
    
    @Autowired
    private final ReportService reportService;

    @GetMapping(path = "/generate")
    public ResponseEntity<Object> generateReport(@RequestParam("reportId") UUID reportId){
        try {
            String reportConfig = reportService.generateReport(reportId);
            
            return ResponseHandler.generateResponse("Generated report successfully!", HttpStatus.OK, reportConfig);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

    @PutMapping(path = "/save")
    public ResponseEntity<Object> saveReport(@RequestParam("reportId") UUID reportId , @RequestBody ReportRequest reportRequest){
        try {
            Reports report = reportService.saveReport(reportId, reportRequest);
            
            return ResponseHandler.generateResponse("Report has been updated successfully!", HttpStatus.OK, report);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }
}
