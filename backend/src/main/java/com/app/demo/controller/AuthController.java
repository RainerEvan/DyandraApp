package com.app.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.demo.payload.request.ReportAuthRequest;
import com.app.demo.payload.response.JwtReportResponse;
import com.app.demo.service.ReportAuthService;
import com.app.demo.utils.ResponseHandler;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {
    
    @Autowired
    private final ReportAuthService reportAuthService;

    @PostMapping(path = "/report")
    public ResponseEntity<Object> authenticateReport(@RequestBody ReportAuthRequest reportAuthRequest){
        try {
            JwtReportResponse jwtReportResponse = reportAuthService.authenticate(reportAuthRequest);
            
            return ResponseHandler.generateResponse("Authenticated successfully!", HttpStatus.OK, jwtReportResponse);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.FORBIDDEN, null);
        }
    }

}
