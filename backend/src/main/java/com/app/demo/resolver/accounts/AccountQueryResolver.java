package com.app.demo.resolver.accounts;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.app.demo.model.Accounts;
import com.app.demo.service.AccountService;

import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class AccountQueryResolver implements GraphQLQueryResolver{
    
    @Autowired
    private final AccountService accountService;

    public List<Accounts> getAllAccounts(){
        return accountService.getAllAccounts();
    }
 }
