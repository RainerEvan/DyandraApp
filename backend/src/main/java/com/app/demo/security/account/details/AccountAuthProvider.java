package com.app.demo.security.account.details;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@AllArgsConstructor
public class AccountAuthProvider implements AuthenticationProvider{

    @Autowired
    private final AccountDetailsServiceImpl accountDetailsService;
    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        try{
            String userId = authentication.getName();
            String password = authentication.getCredentials().toString();

            log.info("Account auth provider checked!");

            UserDetails accountDetails = accountDetailsService.loadUserByUsername(userId);

            if(accountDetails != null && passwordEncoder.matches(password, accountDetails.getPassword())){
                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(accountDetails, accountDetails.getPassword(), accountDetails.getAuthorities());
                token.setDetails(accountDetails);
                return token;
            }
            throw new BadCredentialsException("Invalid credentials!");
        } catch(Exception e){
            throw new BadCredentialsException(e.getMessage());
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
    
    
}
