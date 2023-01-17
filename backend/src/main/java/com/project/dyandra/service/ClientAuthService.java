package com.project.dyandra.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.dyandra.model.Reports;
import com.project.dyandra.payload.request.ClientAuthRequest;
import com.project.dyandra.payload.response.JwtClientResponse;
import com.project.dyandra.security.client.jwt.ClientJwtUtils;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ClientAuthService {
    
    @Autowired
    private final ReportService reportService;
    @Autowired
    private final ClientJwtUtils clientJwtUtils;

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
