package com.vromanyu.secure.notes.config.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class AuthEntryPointJwt implements AuthenticationEntryPoint {

 @Override
 public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
  log.error("Unauthorized error: {}", authException.getMessage());
  response.setContentType("application/json");
  response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

  final Map<String, Object> responseObject = new HashMap<>();
  responseObject.put("status", HttpServletResponse.SC_UNAUTHORIZED);
  responseObject.put("error", "Unauthorized");
  responseObject.put("message", authException.getMessage());
  responseObject.put("path", request.getRequestURI());

  final ObjectMapper mapper = new ObjectMapper();
  mapper.writeValue(response.getOutputStream(), responseObject);
 }
}
