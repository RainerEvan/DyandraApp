package com.app.demo.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.demo.model.Reports;
import com.app.demo.payload.request.ReportRequest;
import com.app.demo.service.ReportService;
import com.app.demo.utils.ResponseHandler;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/admin/report")
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

    @PutMapping(path = "/edit")
    public ResponseEntity<Object> editReport(@RequestParam("reportId") UUID reportId, @RequestBody ReportRequest reportRequest){
        try {
            Reports report = reportService.editReport(reportId, reportRequest);
            
            return ResponseHandler.generateResponse("Report has been updated successfully!", HttpStatus.OK, report);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

    @DeleteMapping(path = "/delete")
    public ResponseEntity<Object> deleteReport(@RequestParam("reportId") UUID reportId){
        try {
            reportService.deleteReport(reportId);
            
            return ResponseHandler.generateResponse("Report has been deleted successfully!", HttpStatus.OK, null);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

}
