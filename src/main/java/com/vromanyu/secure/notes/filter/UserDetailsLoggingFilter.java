package com.vromanyu.secure.notes.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Order
@Component
@Slf4j
public class UserDetailsLoggingFilter extends OncePerRequestFilter {

 @Override
 protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
  if (request.getRequestURI().contains("/api/notes")) {
   Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
   if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    log.info("userDetails: {}", userDetails);
   }
  }
  filterChain.doFilter(request, response);
 }

}
