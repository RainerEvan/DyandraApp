package com.app.demo.service;

import java.time.Instant;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.demo.exception.TokenException;
import com.app.demo.model.ReportTokens;
import com.app.demo.model.Reports;
import com.app.demo.repository.ReportTokenRepository;
import com.app.demo.repository.ReportRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReportTokenService {

    @Autowired
    private final ReportTokenRepository reportTokenRepository;
    @Autowired
    private final ReportRepository reportRepository;

    @Transactional
    public ReportTokens addReportToken(UUID reportId, String token, Instant expirationDate){
        Reports report = reportRepository.findById(reportId)
            .orElseThrow(() -> new IllegalStateException("Report with current id cannot be found"));

        ReportTokens reportToken = new ReportTokens();
        reportToken.setReport(report);
        reportToken.setToken(token);
        reportToken.setExpirationDate(expirationDate);

        return reportTokenRepository.save(reportToken);
    }

    @Transactional
    public ReportTokens verifyExpiration(ReportTokens token){
        if(token.getExpirationDate().compareTo(Instant.now()) < 0){
            reportTokenRepository.delete(token);
            throw new TokenException(token.getToken(), "Token was expired, please make a new request");
        }

        return token;
    }
    
}
