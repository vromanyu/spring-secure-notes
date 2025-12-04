package com.vromanyu.secure.notes.config.security;

import com.vromanyu.secure.notes.config.security.jwt.AuthEntryPointJwt;
import com.vromanyu.secure.notes.filter.AuthTokenFilter;
import com.vromanyu.secure.notes.filter.LoggingFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SpringSecurityConfiguration {

 @Bean
 public SecurityFilterChain customFilterChain(HttpSecurity http, LoggingFilter loggingFilter, AuthEntryPointJwt authEntryPointJwt, AuthTokenFilter authTokenFilter) throws Exception {
  http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
  http.csrf(AbstractHttpConfigurer::disable);
  http.httpBasic(AbstractHttpConfigurer::disable);
  http.exceptionHandling(exception -> exception.authenticationEntryPoint(authEntryPointJwt));
  http.addFilterBefore(authTokenFilter, BasicAuthenticationFilter.class);
  http.addFilterBefore(loggingFilter, AuthTokenFilter.class);
  http.authorizeHttpRequests(requests ->
   requests
    .requestMatchers("/public/**").permitAll()
    .requestMatchers("/error/**").permitAll()
    .requestMatchers("/api/admin/**").hasRole("ADMIN")
    .anyRequest().authenticated());
  return http.build();
 }

 @Bean
 public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration configuration) throws Exception {
  return configuration.getAuthenticationManager();
 }

}
