package com.project.dyandra.service;

import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.dyandra.exception.AbstractGraphQLException;
import com.project.dyandra.model.Accounts;
import com.project.dyandra.model.Roles;
import com.project.dyandra.payload.request.AccountRequest;
import com.project.dyandra.repository.AccountRepository;
import com.project.dyandra.repository.RoleRepository;

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
