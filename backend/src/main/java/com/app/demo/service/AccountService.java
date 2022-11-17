package com.app.demo.service;

import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.demo.exception.AbstractGraphQLException;
import com.app.demo.model.Accounts;
import com.app.demo.model.Roles;
import com.app.demo.payload.request.AccountRequest;
import com.app.demo.repository.AccountRepository;
import com.app.demo.repository.RoleRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AccountService {

    @Autowired
    private final AccountRepository accountRepository;
    @Autowired
    private final RoleRepository roleRepository;
    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public List<Accounts> getAllAccounts(){
        return accountRepository.findAll();
    }

    @Transactional
    public Accounts getAccount(UUID accountId){
        return accountRepository.findById(accountId)
            .orElseThrow(() -> new AbstractGraphQLException("Account with current id cannot be found"));
    }

    @Transactional
    public Accounts getAccountByUserId(String userId){
        return accountRepository.findByUserId(userId)
            .orElseThrow(() -> new AbstractGraphQLException("Account with current user id cannot be found"));
    }
    
    @Transactional
    public Accounts addAccount(AccountRequest accountRequest){
        Roles role = roleRepository.findById(accountRequest.getRoleId())
            .orElseThrow(() -> new AbstractGraphQLException("Role with current id cannot be found"));

        Accounts account = new Accounts();
        account.setUserId(accountRequest.getUserId());
        account.setPassword(passwordEncoder.encode(accountRequest.getPassword()));
        account.setHostName(accountRequest.getHostName());
        account.setRole(role);
        account.setIsActive(true);

        return accountRepository.save(account);
    }
}
