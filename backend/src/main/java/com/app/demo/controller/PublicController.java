package com.app.demo.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.demo.model.Reports;
import com.app.demo.payload.request.ClientAuthRequest;
import com.app.demo.payload.request.ReportRequest;
import com.app.demo.payload.response.ReportTemplateResponse;
import com.app.demo.service.ReportService;
import com.app.demo.utils.ResponseHandler;

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

    @PostMapping(path = "/template")
    public ResponseEntity<Object> getTemplate(@RequestBody ClientAuthRequest clientAuthRequest){
        try {
            ReportTemplateResponse template = reportService.getTemplate(clientAuthRequest);
            
            return ResponseEntity.status(HttpStatus.OK).body(template);
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
