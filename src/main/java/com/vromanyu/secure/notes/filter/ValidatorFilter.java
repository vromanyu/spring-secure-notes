package com.vromanyu.secure.notes.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
public class ValidatorFilter extends OncePerRequestFilter {

 @Override
 protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
  boolean b = Boolean.parseBoolean(request.getHeader("X-Valid-Request"));
  if (!b) {
   log.warn("X-Valid-Request was not properly set");
   response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Request");
   return;
  }
  filterChain.doFilter(request, response);
 }
}
