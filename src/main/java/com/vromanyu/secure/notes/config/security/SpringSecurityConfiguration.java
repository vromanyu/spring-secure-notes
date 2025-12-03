package com.vromanyu.secure.notes.config.security;

import com.vromanyu.secure.notes.filter.LoggingFilter;
import com.vromanyu.secure.notes.filter.ValidatorFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
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
 public SecurityFilterChain customFilterChain(HttpSecurity http, LoggingFilter loggingFilter, ValidatorFilter validatorFilter) throws Exception {
  http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
  http.csrf(AbstractHttpConfigurer::disable);
  http.addFilterBefore(loggingFilter, BasicAuthenticationFilter.class);
  http.addFilterBefore(validatorFilter, LoggingFilter.class);
  http.httpBasic(Customizer.withDefaults())
   .authorizeHttpRequests(requests ->
    requests
     .requestMatchers("/public/**").permitAll()
     .requestMatchers("/error/**").permitAll()
     .requestMatchers("/api/admin/**").hasRole("ADMIN")
     .anyRequest().authenticated());
  return http.build();
 }

}
