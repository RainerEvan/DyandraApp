package com.project.dyandra.resolver.accounts;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.project.dyandra.model.Accounts;
import com.project.dyandra.service.AccountService;

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

    public Accounts getAccountByUserId(String userId){
        return accountService.getAccountByUserId(userId);
    }
 }
