package com.app.demo.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.demo.model.Accounts;
import com.app.demo.payload.request.AccountRequest;
import com.app.demo.repository.AccountRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AccountService {

    @Autowired
    private final AccountRepository accountRepository;

    @Transactional
    public List<Accounts> getAllAccounts(){
        return accountRepository.findAll();
    }
    
    @Transactional
    public Accounts addAccount(AccountRequest accountRequest){
        Accounts account = new Accounts();
        account.setUsername(accountRequest.getUsername());
        account.setPassword(accountRequest.getPassword());

        return accountRepository.save(account);
    }
}
