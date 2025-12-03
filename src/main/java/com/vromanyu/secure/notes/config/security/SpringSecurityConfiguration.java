package com.vromanyu.secure.notes.config.security;

import com.vromanyu.secure.notes.filter.LoggingFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SpringSecurityConfiguration {

 @Bean
 public SecurityFilterChain customFilterChain(HttpSecurity http, LoggingFilter loggingFilter) throws Exception {
  http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS));
  http.csrf(c -> {
   c.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
   c.csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler());
  });
  http.addFilterBefore(loggingFilter, BasicAuthenticationFilter.class);
  http.httpBasic(Customizer.withDefaults())
   .authorizeHttpRequests(requests ->
    requests
     .requestMatchers("/public/**").permitAll()
     .requestMatchers(("/api/csrf")).permitAll()
     .requestMatchers("/error/**").permitAll()
     .requestMatchers("/api/admin/**").hasRole("ADMIN")
     .anyRequest().authenticated());
  return http.build();
 }

}
