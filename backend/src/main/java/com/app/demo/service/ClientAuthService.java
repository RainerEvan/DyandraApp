package com.app.demo.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.app.demo.model.Reports;
import com.app.demo.payload.request.ClientAuthRequest;
import com.app.demo.payload.response.JwtClientResponse;
import com.app.demo.security.client.details.ClientDetailsImpl;
import com.app.demo.security.client.jwt.ClientJwtUtils;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ClientAuthService {
    
    @Autowired
    private final ReportService reportService;
    @Autowired
    private final AuthenticationManager authenticationManager;
    @Autowired
    private final ClientJwtUtils clientJwtUtils;

    @Transactional
    public JwtClientResponse authenticateClient(ClientAuthRequest clientAuthRequest){
        try{
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(clientAuthRequest.getClientId(), clientAuthRequest.getReportId())
            );

            String token = clientJwtUtils.generateJwtToken(authentication);
            ClientDetailsImpl clientDetails = (ClientDetailsImpl) authentication.getPrincipal();

            return new JwtClientResponse(
                token,
                clientJwtUtils.getExpirationFromJwtToken(token).toInstant(),
                clientDetails.getApplicationName()
            );
            
        } catch(Exception e){
            throw new RuntimeException("Client authentication failed! "+e.getMessage());
        }
    }
}
