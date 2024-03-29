package com.project.dyandra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.dyandra.payload.response.ReportTemplateResponse;
import com.project.dyandra.service.ReportService;
import com.project.dyandra.utils.ResponseHandler;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/client")
@AllArgsConstructor
public class ClientController {
    @Autowired
    private final ReportService reportService;

    @GetMapping(path = "/report")
    public ResponseEntity<Object> getTemplate(@RequestParam("token") String token){
        try {
            ReportTemplateResponse template = reportService.getTemplate(token);
            
            return ResponseEntity.status(HttpStatus.OK).body(template);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }
}
