package com.vromanyu.secure.notes.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfiguration {

 @Bean
 public SecurityFilterChain customFilterChain(HttpSecurity http) throws Exception {
  http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
  http.csrf(AbstractHttpConfigurer::disable);
  http.httpBasic(Customizer.withDefaults())
   .authorizeHttpRequests(requests -> requests.anyRequest().authenticated());
  return http.build();
 }

 @Bean
 public UserDetailsService userDetailsService(DataSource dataSource) {
  JdbcUserDetailsManager userDetailsManager = new JdbcUserDetailsManager(dataSource);
  userDetailsManager.createUser(User.withUsername("admin").password("{noop}1234").roles("ADMIN").build());
  userDetailsManager.createUser(User.withUsername("user1").password("{noop}1234").roles("USER").build());
  userDetailsManager.createUser(User.withUsername("user2").password("{noop}1234").roles("USER").build());
  return userDetailsManager;
 }

}
