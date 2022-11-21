package com.app.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.demo.payload.response.ReportTemplateResponse;
import com.app.demo.service.ReportService;
import com.app.demo.utils.ResponseHandler;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/client/report")
@AllArgsConstructor
public class ClientController {
    @Autowired
    private final ReportService reportService;

    @GetMapping(path = "/template")
    public ResponseEntity<Object> getTemplate(@RequestParam("reportId") String reportId){
        try {
            ReportTemplateResponse template = reportService.getTemplate(reportId);
            
            return ResponseEntity.status(HttpStatus.OK).body(template);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }
}
