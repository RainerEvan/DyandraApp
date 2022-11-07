package com.app.demo.security.report.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.app.demo.model.Applications;
import com.app.demo.service.ApplicationService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ReportTokenFilter extends OncePerRequestFilter{
    
    @Autowired
    private ReportJwtUtils jwtUtils;
    @Autowired
    private ApplicationService applicationService;;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {
        try{
            log.info("Report Auth - Request URL path: {}, Request content type: {}",request.getRequestURI(), request.getContentType());
            String jwt = parseJwt(request);
            if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
                String clientId = jwtUtils.getClientFromJwtToken(jwt);
                Applications application = applicationService.getApplication(clientId);
                log.info("Application authenticated: {}", application.getName());
            }
        } catch(Exception e){
            log.error("Cannot set report authentication: {}", e);
        }
        filterChain.doFilter(request, response);
    }

    private String parseJwt(HttpServletRequest request){
        String headerAuth = request.getHeader("Report-Token");
        if (StringUtils.hasText(headerAuth)) {
          return headerAuth.substring(0, headerAuth.length());
        }
        return null;
    }
}
