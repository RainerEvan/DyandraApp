package com.app.demo.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.demo.model.Applications;
import com.app.demo.model.ReportTokens;
import com.app.demo.model.Reports;
import com.app.demo.payload.request.ClientAuthRequest;
import com.app.demo.payload.response.JwtReportResponse;
import com.app.demo.repository.ApplicationRepository;
import com.app.demo.repository.ReportRepository;
import com.app.demo.security.report.jwt.ReportJwtUtils;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ReportAuthService {
    
    @Autowired
    private final ApplicationRepository applicationRepository;
    @Autowired
    private final ReportRepository reportRepository;
    @Autowired
    private final ReportJwtUtils reportJwtUtils;
    @Autowired
    private final ReportTokenService reportTokenService;

    @Transactional
    public JwtReportResponse authenticate(ClientAuthRequest reportAuthRequest){
        try{
            Applications application = applicationRepository.findByClientId(reportAuthRequest.getClientId())
                .orElseThrow(() -> new IllegalStateException("Client with current id cannot be found"));
            
            Reports report = reportRepository.findByReportId(reportAuthRequest.getReportId())
                .orElseThrow(() -> new IllegalStateException("Report with current id cannot be found"));

            if(application.getClientId().equals(report.getConnection().getApplication().getClientId())){
                String token = reportJwtUtils.generateJwtToken(report);

                ReportTokens reportToken = reportTokenService.addReportToken(report.getId(), token, reportJwtUtils.getExpirationFromJwtToken(token).toInstant());

                return new JwtReportResponse(
                    token,
                    reportToken.getExpirationDate(),
                    report.getTitle()
                );
            }
            
            throw new IllegalStateException("Invalid credentials to access report!"); 
        } catch(Exception e){
            throw new IllegalStateException(e.getMessage());
        }
    } 

}
