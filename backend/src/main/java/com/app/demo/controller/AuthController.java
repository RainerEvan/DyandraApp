package com.app.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.demo.payload.request.ClientAuthRequest;
import com.app.demo.payload.request.LoginUIDMRequest;
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

    @GetMapping(path = "/unauthorized")
    public ResponseEntity<Object> unauthorized(){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Request unauthorized!");
    }

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
    public ResponseEntity<Object> login(@RequestBody LoginUIDMRequest loginUIDMRequest){
        try {
            JwtAccountResponse jwtAccountResponse = accountAuthService.loginAccount(loginUIDMRequest);
            return ResponseEntity.status(HttpStatus.OK).body(jwtAccountResponse);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }
}
