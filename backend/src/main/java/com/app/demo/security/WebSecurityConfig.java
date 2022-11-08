package com.app.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
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
import com.app.demo.security.client.jwt.ClientTokenFilter;

import lombok.AllArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@AllArgsConstructor
public class WebSecurityConfig{

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

    @Configuration
    @Order(1)
    @AllArgsConstructor
    public static class ClientConfigurationAdapter{

        @Bean
        public ClientTokenFilter clientTokenFilter(){
            return new ClientTokenFilter();
        }

        @Bean
        public SecurityFilterChain filterChainClient(HttpSecurity http) throws Exception{
            http.cors().and().csrf().disable()
                .httpBasic().disable();
                // .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                // .and()
                // .authorizeRequests()
                // .antMatchers("/api/auth/**").permitAll()
                // .antMatchers("/graphiql", "/vendor/**").permitAll()
                // .antMatchers("/graphql").permitAll()
                // .anyRequest().authenticated()
                // .and()
                // .addFilterBefore(clientTokenFilter(), UsernamePasswordAuthenticationFilter.class)
                // .httpBasic()
                // .and()
                // .logout()
                //     .clearAuthentication(true)
                //     .invalidateHttpSession(true)
                //     .deleteCookies("JSESSIONID")
                //     .permitAll();
    
            return http.build();
        }

    }

    @Configuration
    @Order(2)
    @AllArgsConstructor
    public static class AdminConfigurationAdapter{

        @Autowired
        private final AccountEntryPoint accountEntryPoint;

        @Bean
        public AccountTokenFilter accountTokenFilter(){
            return new AccountTokenFilter();
        }
        
        @Bean
        public SecurityFilterChain filterChainAccount(HttpSecurity http) throws Exception{
            http.cors().and().csrf().disable()
                .httpBasic().disable();
                // .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                // .and()
                // .exceptionHandling().authenticationEntryPoint(accountEntryPoint)
                // .and()
                // .authorizeRequests()
                // .antMatchers("**/admin/**").hasAuthority("ADMIN")
                // .antMatchers("/api/auth/**").permitAll()
                // .antMatchers("/graphiql", "/vendor/**").permitAll()
                // .antMatchers("/graphql").permitAll()
                // .anyRequest().authenticated()
                // .and()
                // .addFilterBefore(accountTokenFilter(), UsernamePasswordAuthenticationFilter.class)
                // .httpBasic()
                // .and()
                // .logout()
                //     .clearAuthentication(true)
                //     .invalidateHttpSession(true)
                //     .deleteCookies("JSESSIONID")
                //     .permitAll();
    
            return http.build();
        }

    }
}
