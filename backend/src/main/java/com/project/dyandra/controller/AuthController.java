package com.project.dyandra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.dyandra.payload.request.ClientAuthRequest;
import com.project.dyandra.payload.request.LoginRequest;
import com.project.dyandra.payload.response.JwtAccountResponse;
import com.project.dyandra.payload.response.JwtClientResponse;
import com.project.dyandra.service.AuthService;
import com.project.dyandra.utils.ResponseHandler;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {
    
    @Autowired
    private final AuthService authService;

    @GetMapping(path = "/unauthorized")
    public ResponseEntity<Object> unauthorized(){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Request unauthorized!");
    }

    @PostMapping(path = "/login")
    public ResponseEntity<Object> login(@RequestBody LoginRequest loginRequest){
        try {
            JwtAccountResponse jwtAccountResponse = authService.loginAccount(loginRequest);
            return ResponseEntity.status(HttpStatus.OK).body(jwtAccountResponse);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

    @PostMapping(path = "/client")
    public ResponseEntity<Object> authenticate(@RequestBody ClientAuthRequest clientAuthRequest){
        try {
            JwtClientResponse jwtClientResponse = authService.authenticateClient(clientAuthRequest);
            
            return ResponseEntity.status(HttpStatus.OK).body(jwtClientResponse);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.FORBIDDEN, null);
        }
    }
    
}
