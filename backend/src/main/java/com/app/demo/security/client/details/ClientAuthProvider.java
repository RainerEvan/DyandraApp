package com.app.demo.security.client.details;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.app.demo.service.ReportService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@AllArgsConstructor
public class ClientAuthProvider implements AuthenticationProvider{

    @Autowired
    private final ClientDetailsServiceImpl clientDetailsService;
    @Autowired
    private final ReportService reportService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String clientId = authentication.getName();
        String reportId = authentication.getCredentials().toString();

        log.info("Client auth provider checked! {} + {}",clientId,reportId);

        UserDetails clientDetails = clientDetailsService.loadUserByUsername(reportId);

        if(clientDetails != null){
            if(reportService.verifyClientReport(clientId, reportId)){
                return new UsernamePasswordAuthenticationToken(clientDetails, clientDetails.getPassword(), clientDetails.getAuthorities());
            }
        }
        
        throw new BadCredentialsException("Invalid credentials");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
    
    
}

