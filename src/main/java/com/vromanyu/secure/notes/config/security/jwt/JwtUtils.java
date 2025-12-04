package com.vromanyu.secure.notes.config.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
@Getter
@Setter
@NoArgsConstructor
public class JwtUtils {

 @Value("${jwt.secret}")
 private String jwtSecret;

 public String getJwtFromHeader(HttpServletRequest request) {
  String authorizationHeader = request.getHeader("Authorization");
  log.info("Authorization Header: {}", authorizationHeader);
  if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
   return authorizationHeader.substring(7);
  }
  return null;
 }

 public String generateTokenFromUsername(UserDetails userDetails) {
  String username = userDetails.getUsername();
  return JWT.create()
   .withSubject(username)
   .withIssuedAt(new Date())
   .withIssuer("secure-notes")
   .withExpiresAt(new Date(new Date().getTime() + TimeUnit.MINUTES.toMillis(25)))
   .sign(Algorithm.HMAC256(jwtSecret));
 }

 public String getUsernameFromToken(String token) {
  return JWT.require(Algorithm.HMAC256(jwtSecret)).build().verify(token).getSubject();
 }

 public boolean validateToken(String token) {
  try {
   JWT.require(Algorithm.HMAC256(jwtSecret)).build().verify(token);
   return true;
  } catch (JWTVerificationException e) {
   log.warn("JWT verification failed: {}", e.getMessage());
   return false;
  }
 }
}
