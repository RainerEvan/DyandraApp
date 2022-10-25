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
    public ResponseEntity<Object> addSourcePath(@RequestBody SourcePathRequest sourcePathRequest){
        try {
            SourcePaths sourcePath = sourcePathService.addSourcePath(sourcePathRequest);
            
            return ResponseHandler.generateResponse("Source path has been added successfully!", HttpStatus.OK, sourcePath);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

    @DeleteMapping(path = "/delete")
    public ResponseEntity<Object> deleteSourcePath(@RequestParam("sourcePathId") UUID sourcePathId){
        try {
            sourcePathService.deleteSourcePath(sourcePathId);
            
            return ResponseHandler.generateResponse("Source path has been deleted successfully!", HttpStatus.OK, null);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }
}
