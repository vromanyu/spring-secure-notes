package com.vromanyu.secure.notes.filter;

import com.vromanyu.secure.notes.config.security.jwt.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthTokenFilter extends OncePerRequestFilter {

 private final JwtUtils jwtUtils;
 private final UserDetailsService userDetailsService;

 @Override
 protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
  log.info("AuthTokenFilter called for URI: {}", request.getRequestURI());
  try {
   String jwt = jwtUtils.getJwtFromHeader(request);
   log.info("jwt: {}", jwt);
   if (jwt != null && jwtUtils.validateToken(jwt)) {
    String username = jwtUtils.getUsernameFromToken(jwt);
    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    log.info("JWT roles: {}", userDetails.getAuthorities());
    authentication.setDetails(new WebAuthenticationDetails(request));
    SecurityContextHolder.getContext().setAuthentication(authentication);
   }
  } catch (Exception e) {
   log.warn(e.getMessage());
  }
  filterChain.doFilter(request, response);
 }
}
