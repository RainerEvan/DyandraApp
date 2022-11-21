package com.app.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.app.demo.model.Accounts;
import com.app.demo.payload.request.LoginRequest;
import com.app.demo.payload.response.JwtAccountResponse;
import com.app.demo.repository.AccountRepository;
import com.app.demo.security.account.details.AccountDetailsImpl;
import com.app.demo.security.account.jwt.AccountJwtUtils;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AccountAuthService {
    
    @Autowired
    private final AccountRepository accountRepository;
    @Autowired
    private final AuthenticationManager authenticationManager;
    @Autowired
    private final AccountJwtUtils accountJwtUtils;

    public Accounts getCurrentAccount(){
        String principal = (String) SecurityContextHolder.getContext().getAuthentication().getName();
        
        return accountRepository.findByUserId(principal)
            .orElseThrow(() -> new UsernameNotFoundException("Account with current user id cannot be found: "+principal));
    }

    public JwtAccountResponse loginAccount(LoginRequest loginRequest){
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUserId(), loginRequest.getPassword())
            );

            String token = accountJwtUtils.generateJwtToken(authentication);
            AccountDetailsImpl accountDetails = (AccountDetailsImpl) authentication.getPrincipal();
            List<String> roles = accountDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

            return new JwtAccountResponse(
                token, 
                accountJwtUtils.getExpirationFromJwtToken(token).toInstant(), 
                accountDetails.getUsername(),
                accountDetails.getIsActive(),
                roles
            );
        } catch (Exception e) {
            throw new RuntimeException("Login failed! "+e.getMessage());
        }
    }
    
}
