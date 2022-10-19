package com.app.demo.service;

import java.time.Instant;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.app.demo.exception.TokenRefreshException;
import com.app.demo.model.ReportRefreshTokens;
import com.app.demo.model.Reports;
import com.app.demo.repository.ReportRefreshTokenRepository;
import com.app.demo.repository.ReportRepository;
import com.app.demo.security.report.jwt.ReportJwtUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReportRefreshTokenService {

    @Value("${jwt.jwtRefreshExpirationMs}")
    private Long jwtRefreshExpirationMs;
    
    @Autowired
    private final ReportRefreshTokenRepository reportRefreshTokenRepository;
    @Autowired
    private final ReportRepository reportRepository;
    @Autowired
    private final ReportJwtUtils reportJwtUtils;

    @Transactional
    public ReportRefreshTokens addReportRefreshToken(UUID reportId){
        Reports report = reportRepository.findById(reportId)
            .orElseThrow(() -> new IllegalStateException("Report with current token cannot be found"));

        ReportRefreshTokens reportRefreshToken = new ReportRefreshTokens();
        reportRefreshToken.setReport(report);
        reportRefreshToken.setExpirationDate(Instant.now().plusMillis(jwtRefreshExpirationMs));
        reportRefreshToken.setToken(UUID.randomUUID().toString().replaceAll("-", "").toLowerCase());

        return reportRefreshTokenRepository.save(reportRefreshToken);
    }

    @Transactional
    public ReportRefreshTokens verifyExpiration(ReportRefreshTokens refreshToken){
        if(refreshToken.getExpirationDate().compareTo(Instant.now()) < 0){
            reportRefreshTokenRepository.delete(refreshToken);
            throw new TokenRefreshException(refreshToken.getToken(), "Refresh token was expired, please make a new request");
        }

        return refreshToken;
    }
    
    @Transactional
    public String refreshToken(String refreshToken){
        ReportRefreshTokens reportRefreshToken = reportRefreshTokenRepository.findByToken(refreshToken)
            .orElseThrow(() -> new TokenRefreshException(refreshToken, "Refresh token cannot be found"));

        reportRefreshToken = verifyExpiration(reportRefreshToken);

        if(reportRefreshToken!=null){
            String accessToken = reportJwtUtils.generateJwtToken(reportRefreshToken.getReport());
            return accessToken;
        }

        return null;
    }
}
