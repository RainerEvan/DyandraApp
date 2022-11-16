package com.app.demo.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.app.demo.security.account.details.AccountAuthProvider;
import com.app.demo.security.account.jwt.AccountTokenFilter;
import com.app.demo.security.client.jwt.ClientTokenFilter;

import lombok.AllArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@AllArgsConstructor
public class WebSecurityConfig{

    @Autowired
    private final AuthEntryPoint authEntryPoint;
    @Autowired
    private final AccountAuthProvider accountAuthProvider;

    @Bean
    public AccountTokenFilter accountTokenFilter(){
        return new AccountTokenFilter();
    }

    @Bean
    public ClientTokenFilter clientTokenFilter(){
        return new ClientTokenFilter();
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder, UserDetailsService userDetailsService) throws Exception{
        return http.getSharedObject(AuthenticationManagerBuilder.class)
            .authenticationProvider(accountAuthProvider)
            // .authenticationProvider(clientAuthProvider)
            .build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.debug(false)
        .ignoring()
        .antMatchers("/css/**", "/js/**", "/img/**", "/lib/**", "/favicon.ico");
    }
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.cors().and().csrf().disable()
            // .httpBasic().disable();
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .exceptionHandling().authenticationEntryPoint(authEntryPoint)
            .and()
            .authorizeRequests()
            .antMatchers("**/admin/**").hasAuthority("ADMIN")
            .antMatchers("/api/auth/**").permitAll()
            .antMatchers("/graphiql", "/vendor/**").permitAll()
            .antMatchers("/graphql").permitAll()
            .anyRequest().authenticated()
            .and()
            .addFilterBefore(accountTokenFilter(), UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(clientTokenFilter(), UsernamePasswordAuthenticationFilter.class)
            .httpBasic();
            
        return http.build();
    }
}
