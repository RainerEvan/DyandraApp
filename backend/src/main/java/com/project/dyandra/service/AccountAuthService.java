package com.project.dyandra.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.project.dyandra.payload.request.LoginRequest;
import com.project.dyandra.payload.response.JwtAccountResponse;
import com.project.dyandra.security.account.details.AccountDetailsImpl;
import com.project.dyandra.security.account.jwt.AccountJwtUtils;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AccountAuthService {
    
    @Autowired
    private final AuthenticationManager authenticationManager;
    @Autowired
    private final AccountJwtUtils accountJwtUtils;

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
