package com.app.demo.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.demo.model.Applications;
import com.app.demo.payload.request.ApplicationRequest;
import com.app.demo.service.ApplicationService;
import com.app.demo.utils.ResponseHandler;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/application")
@AllArgsConstructor
public class ApplicationController {
    
    @Autowired
    private final ApplicationService applicationService;

    @PostMapping(path = "/add")
    public ResponseEntity<Object> addApplication(@RequestBody ApplicationRequest applicationRequest){
        try {
            Applications application = applicationService.addApplication(applicationRequest);
            
            return ResponseHandler.generateResponse("Application has been added successfully!", HttpStatus.OK, application);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

    @DeleteMapping(path = "/delete")
    public ResponseEntity<Object> deleteApplication(@RequestParam("applicationId") UUID applicationId){
        try {
            applicationService.deleteApplication(applicationId);
            
            return ResponseHandler.generateResponse("Application has been deleted successfully!", HttpStatus.OK, null);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }
}
