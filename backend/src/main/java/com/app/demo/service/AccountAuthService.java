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
import com.app.demo.security.account.details.UserDetailsImpl;
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
        
        return accountRepository.findByUsername(principal)
            .orElseThrow(() -> new UsernameNotFoundException("Account with current username cannot be found: "+principal));
    }

    public JwtAccountResponse loginAccount(LoginRequest loginRequest){
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String token = accountJwtUtils.generateJwtToken(authentication);
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

            return new JwtAccountResponse(
                token, 
                accountJwtUtils.getExpirationFromJwtToken(token).toInstant(), 
                userDetails.getUsername(),
                userDetails.getIsActive(),
                roles
            );
        } catch (Exception e) {
            throw new RuntimeException("Invalid username or password! "+e.getMessage());
        }
    }
    
}
