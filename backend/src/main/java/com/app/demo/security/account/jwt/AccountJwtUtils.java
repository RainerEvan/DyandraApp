package com.app.demo.security.account.jwt;

import org.springframework.stereotype.Component;

import com.app.demo.security.account.details.AccountDetailsImpl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class AccountJwtUtils {

    @Value("${jwt.jwtAccountSecretKey}")
    private String jwtSecret;
    
    @Value("${jwt.jwtExpirationMs}")
    private Long jwtExpirationMs;

    public String generateJwtToken(Authentication authentication) {
        AccountDetailsImpl accountPrincipal = (AccountDetailsImpl) authentication.getPrincipal();
        return Jwts.builder()
            .setSubject((accountPrincipal.getUsername()))
            .setIssuedAt(new Date())
            .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
            .signWith(SignatureAlgorithm.HS512, jwtSecret)
            .compact();
    }

    public String getUsernameFromJwtToken(String token) {
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

    public Long getJwtExpirationMs(){
        return jwtExpirationMs;
    }
}
