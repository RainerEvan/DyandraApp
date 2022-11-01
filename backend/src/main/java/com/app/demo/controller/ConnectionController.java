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

import com.app.demo.model.Connections;
import com.app.demo.payload.request.ConnectionRequest;
import com.app.demo.service.ConnectionService;
import com.app.demo.utils.ResponseHandler;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/connection")
@AllArgsConstructor
public class ConnectionController {
    
    @Autowired
    private final ConnectionService connectionService;

    @PostMapping(path = "/admin/add")
    public ResponseEntity<Object> addConnection(@RequestBody ConnectionRequest connectionRequest){
        try {
            Connections connection = connectionService.addConnection(connectionRequest);
            
            return ResponseHandler.generateResponse("Connection has been added successfully!", HttpStatus.OK, connection);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

    @DeleteMapping(path = "/admin/delete")
    public ResponseEntity<Object> deleteConnection(@RequestParam("connectionId") UUID connectionId){
        try {
            connectionService.deleteConnection(connectionId);
            
            return ResponseHandler.generateResponse("Connection has been deleted successfully!", HttpStatus.OK, null);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }
}

