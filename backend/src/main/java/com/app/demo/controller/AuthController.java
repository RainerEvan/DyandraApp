package com.app.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.demo.payload.request.ClientAuthRequest;
import com.app.demo.payload.request.LoginRequest;
import com.app.demo.payload.response.JwtAccountResponse;
import com.app.demo.payload.response.JwtClientResponse;
import com.app.demo.service.AccountAuthService;
import com.app.demo.service.ClientAuthService;
import com.app.demo.utils.ResponseHandler;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {
    
    @Autowired
    private final ClientAuthService clientAuthService;
    @Autowired
    private final AccountAuthService accountAuthService;

    @PostMapping(path = "/client")
    public ResponseEntity<Object> authenticate(@RequestBody ClientAuthRequest clientAuthRequest){
        try {
            JwtClientResponse jwtClientResponse = clientAuthService.authenticateClient(clientAuthRequest);
            
            return ResponseEntity.status(HttpStatus.OK).body(jwtClientResponse);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.FORBIDDEN, null);
        }
    }

    @PostMapping(path = "/login")
    public ResponseEntity<Object> login(@RequestBody LoginRequest loginRequest){
        try {
            JwtAccountResponse jwtAccountResponse = accountAuthService.loginAccount(loginRequest);
            return ResponseEntity.status(HttpStatus.OK).body(jwtAccountResponse);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }
}
