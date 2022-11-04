package com.app.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.app.demo.security.account.jwt.AccountEntryPoint;
import com.app.demo.security.account.jwt.AccountTokenFilter;
import com.app.demo.security.report.jwt.ReportTokenFilter;

import lombok.AllArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@AllArgsConstructor
public class WebSecurityConfig{

    @Autowired
    private final AccountEntryPoint accountEntryPoint;

    @Bean
    public AccountTokenFilter accountTokenFilter(){
        return new AccountTokenFilter();
    }

    @Bean
    public ReportTokenFilter reportTokenFilter(){
        return new ReportTokenFilter();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public AuthenticationManager authManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder, UserDetailsService userDetailsService) throws Exception{
        return http.getSharedObject(AuthenticationManagerBuilder.class)
            .userDetailsService(userDetailsService)
            .passwordEncoder(bCryptPasswordEncoder)
            .and()
            .build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.cors().and().csrf().disable()
			.exceptionHandling().authenticationEntryPoint(accountEntryPoint).and()
            .authorizeRequests()
            .antMatchers("**/admin/**").hasRole("ADMIN")
            .antMatchers("/api/auth/**").permitAll()
            .antMatchers("/api/report/**").permitAll()
            .antMatchers("/graphiql", "/vendor/**").permitAll()
            .antMatchers("/graphql").permitAll()
            .anyRequest().authenticated()
            .and()
            .httpBasic()
            .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .addFilterBefore(reportTokenFilter(), UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(accountTokenFilter(), UsernamePasswordAuthenticationFilter.class)
            .logout()
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll();

        return http.build();
    }

}
