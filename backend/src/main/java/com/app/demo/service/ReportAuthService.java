package com.app.demo.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.demo.model.Applications;
import com.app.demo.model.ReportRefreshTokens;
import com.app.demo.model.Reports;
import com.app.demo.payload.request.ReportAuthRequest;
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
    private final ReportRefreshTokenService reportRefreshTokenService;

    @Transactional
    public JwtReportResponse authenticate(ReportAuthRequest reportAuthRequest){
        try{
            Reports report = reportRepository.findByReportId(reportAuthRequest.getReportId())
                .orElseThrow(() -> new IllegalStateException("Report with current id cannot be found"));

            Applications application = applicationRepository.findByClientId(reportAuthRequest.getClientId())
                .orElseThrow(() -> new IllegalStateException("Client with current id cannot be found"));

            if(application.getClientId().equals(report.getConnection().getApplication().getClientId())){
                String accessToken = reportJwtUtils.generateJwtToken(report);

                ReportRefreshTokens refreshTokens = reportRefreshTokenService.addReportRefreshToken(report.getId());

                return new JwtReportResponse(
                    accessToken,
                    reportJwtUtils.getExpirationFromJwtToken(accessToken).toInstant(),
                    refreshTokens.getToken(),
                    report.getTitle()
                );
            }
            
            throw new IllegalStateException("Invalid credentials to access report!"); 
        } catch(Exception e){
            throw new IllegalStateException(e.getMessage());
        }
    } 

}
