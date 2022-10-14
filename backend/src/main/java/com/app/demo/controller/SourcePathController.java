package com.app.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.demo.model.SourcePaths;
import com.app.demo.payload.request.SourcePathRequest;
import com.app.demo.service.SourcePathService;
import com.app.demo.utils.ResponseHandler;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/source-path")
@AllArgsConstructor
public class SourcePathController {
    
    @Autowired
    private final SourcePathService sourcePathService;

    @PostMapping(path = "/add")
    public ResponseEntity<Object> addApplication(@RequestBody SourcePathRequest sourcePathRequest){
        try {
            SourcePaths sourcePath = sourcePathService.addSourcePath(sourcePathRequest);
            
            return ResponseHandler.generateResponse("SourcePath has been added successfully!", HttpStatus.OK, sourcePath);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }
}
