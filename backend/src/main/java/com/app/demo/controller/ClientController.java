package com.app.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.demo.payload.request.ClientAuthRequest;
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

    @PostMapping(path = "/template")
    public ResponseEntity<Object> getTemplate(@RequestBody ClientAuthRequest clientAuthRequest){
        try {
            ReportTemplateResponse template = reportService.getTemplate(clientAuthRequest);
            
            return ResponseEntity.status(HttpStatus.OK).body(template);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }
}
