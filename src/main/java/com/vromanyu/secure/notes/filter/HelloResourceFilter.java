package com.vromanyu.secure.notes.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class HelloResourceFilter extends OncePerRequestFilter {

 private int counter = 0;

 @Override
 protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
  if (request.getRequestURI().contains("/hello")) {
   counter++;
   response.addHeader("Accesses", String.valueOf(counter));
  }
  filterChain.doFilter(request, response);
 }
}
