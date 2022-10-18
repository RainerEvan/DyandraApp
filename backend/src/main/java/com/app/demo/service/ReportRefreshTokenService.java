package com.app.demo.service;

import java.time.Instant;
import java.util.Optional;
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

    @Transactional
    public Optional<ReportRefreshTokens> getReportRefreshToken(String token){
        return reportRefreshTokenRepository.findByToken(token);
    }

    public ReportRefreshTokens addReportRefreshToken(UUID reportId){
        Reports report = reportRepository.findById(reportId)
            .orElseThrow(() -> new IllegalStateException("Report with current token cannot be found"));

        ReportRefreshTokens reportRefreshToken = new ReportRefreshTokens();
        reportRefreshToken.setReport(report);
        reportRefreshToken.setExpiryDate(Instant.now().plusMillis(jwtRefreshExpirationMs));
        reportRefreshToken.setToken(UUID.randomUUID().toString().replaceAll("-", "").toLowerCase());

        return reportRefreshTokenRepository.save(reportRefreshToken);
    }

    @Transactional
    public ReportRefreshTokens verifyExpiration(ReportRefreshTokens refreshToken){
        if(refreshToken.getExpiryDate().compareTo(Instant.now()) < 0){
            reportRefreshTokenRepository.delete(refreshToken);
            throw new TokenRefreshException(refreshToken.getToken(), "Refresh token was expired, please make a new request");
        }

        return refreshToken;
    }
}
