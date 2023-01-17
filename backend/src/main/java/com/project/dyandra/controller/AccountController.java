package com.project.dyandra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.dyandra.model.Accounts;
import com.project.dyandra.payload.request.AccountRequest;
import com.project.dyandra.service.AccountService;
import com.project.dyandra.utils.ResponseHandler;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/admin/account")
@AllArgsConstructor
public class AccountController {
    
    @Autowired
    private final AccountService accountService;

    @PostMapping(path = "/add")
    public ResponseEntity<Object> addAccount(@RequestBody AccountRequest accountRequest){
        try {
            Accounts account = accountService.addAccount(accountRequest);
            
            return ResponseHandler.generateResponse("Account has been added successfully!", HttpStatus.OK, account);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }
}
