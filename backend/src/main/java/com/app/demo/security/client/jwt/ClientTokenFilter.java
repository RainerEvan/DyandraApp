package com.app.demo.security.client.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.app.demo.service.ReportService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ClientTokenFilter extends OncePerRequestFilter{
    
    @Autowired
    private ClientJwtUtils jwtUtils;
    @Autowired
    private ReportService reportService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {
        try{
            log.info("Client Auth - Request URL path: {}, Request content type: {}",request.getRequestURI(), request.getContentType());
            String jwt = parseJwt(request);
            if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
                String clientId = jwtUtils.getClientFromJwtToken(jwt);
                String reportId = jwtUtils.getReportFromJwtToken(jwt);

                if(reportService.verifyClientReport(clientId, reportId)){
                    log.info("Successfully set client authentication");
                }
            }
        } catch(Exception e){
            log.error("Cannot set report authentication: {}", e);
        }
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();

        return path.contains("/admin");
    }

    private String parseJwt(HttpServletRequest request){
        String headerAuth = request.getHeader("Client-Token");
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Client ")) {
          return headerAuth.substring(7, headerAuth.length());
        }
        return null;
    }
}
