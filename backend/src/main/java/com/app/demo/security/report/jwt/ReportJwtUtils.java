package com.app.demo.security.report.jwt;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.app.demo.model.Reports;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ReportJwtUtils {
    
    @Value("${jwt.jwtSecretKey}")
    private String jwtSecret;

    @Value("${jwt.jwtExpirationMs}")
    private Long jwtExpirationMs;

    public String generateJwtToken(Reports report){
        return Jwts.builder()
            .setIssuer(report.getConnection().getApplication().getClientId())
            .setSubject(report.getReportId())
            .setIssuedAt(new Date())
            .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
            .signWith(SignatureAlgorithm.HS512, jwtSecret)
            .compact();
    }

    public String getReportFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    public Date getExpirationFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getExpiration();
    }

    public boolean validateJwtToken(String authToken) {
        try {
        Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
        return true;
        } catch (SignatureException e) {
        log.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
        log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
        log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
        log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
        log.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }

}
