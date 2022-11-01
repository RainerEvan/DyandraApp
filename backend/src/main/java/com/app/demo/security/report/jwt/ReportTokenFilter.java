package com.app.demo.security.report.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.app.demo.service.ReportTokenService;

@Component
public class ReportTokenFilter extends OncePerRequestFilter{
    
    @Autowired
    private ReportJwtUtils reportJwtUtils;
    @Autowired
    private ReportTokenService reportTokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {
        try{
            String jwtToken = parseJwt(request);
            if(jwtToken != null && reportJwtUtils.validateJwtToken(jwtToken)){

            }
        } catch(Exception e){
        }
        filterChain.doFilter(request, response);
    }

    private String parseJwt(HttpServletRequest request){
        String headerAuth = request.getHeader("Authorization");
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
          return headerAuth.substring(7, headerAuth.length());
        }
        return null;
    }
}
