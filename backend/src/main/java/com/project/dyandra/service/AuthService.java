package com.project.dyandra.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.project.dyandra.model.Reports;
import com.project.dyandra.payload.request.ClientAuthRequest;
import com.project.dyandra.payload.request.LoginRequest;
import com.project.dyandra.payload.response.JwtAccountResponse;
import com.project.dyandra.payload.response.JwtClientResponse;
import com.project.dyandra.security.account.details.AccountDetailsImpl;
import com.project.dyandra.security.account.jwt.AccountJwtUtils;
import com.project.dyandra.security.client.jwt.ClientJwtUtils;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthService {

    @Autowired
    private final AuthenticationManager authenticationManager;
    @Autowired
    private final AccountJwtUtils accountJwtUtils;
    @Autowired
    private final ReportService reportService;
    @Autowired
    private final ClientJwtUtils clientJwtUtils;

    public JwtAccountResponse loginAccount(LoginRequest loginRequest){
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUserId(), loginRequest.getPassword())
            );

            String token = accountJwtUtils.generateJwtToken(authentication);
            AccountDetailsImpl accountDetails = (AccountDetailsImpl) authentication.getPrincipal();
            String role = accountDetails.getAuthorities().iterator().next().getAuthority();

            return new JwtAccountResponse(
                token, 
                accountJwtUtils.getExpirationFromJwtToken(token).toInstant(), 
                accountDetails.getUsername(),
                accountDetails.getIsActive(),
                role
            );
        } catch (Exception e) {
            throw new RuntimeException("Login failed! "+e.getMessage());
        }
    }


    @Transactional
    public JwtClientResponse authenticateClient(ClientAuthRequest clientAuthRequest){
        try{
            if(reportService.verifyClientReport(clientAuthRequest.getClientId(), clientAuthRequest.getReportId())){
                Reports report = reportService.getReportByReportId(clientAuthRequest.getReportId());

                String token = clientJwtUtils.generateJwtToken(report);

                return new JwtClientResponse(
                    token,
                    clientJwtUtils.getExpirationFromJwtToken(token).toInstant(),
                    report.getConnection().getApplication().getName()
                );
            }
            
            return null;
        } catch(Exception e){
            throw new RuntimeException("Client authentication failed! "+e.getMessage());
        }
    }
}
