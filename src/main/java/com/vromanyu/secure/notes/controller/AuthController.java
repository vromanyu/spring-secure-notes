package com.vromanyu.secure.notes.controller;

import com.vromanyu.secure.notes.config.security.jwt.JwtUtils;
import com.vromanyu.secure.notes.dto.LoginRequestDto;
import com.vromanyu.secure.notes.dto.LoginResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/public/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

 private final JwtUtils jwtUtils;
 private final AuthenticationManager authenticationManager;

 @PostMapping("/sign-in")
 public ResponseEntity<?> authenticateUser(@RequestBody LoginRequestDto loginRequest){
  Authentication authentication = null;
  try {
   authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password()));
  } catch (AuthenticationException e) {
   final Map<String, Object> errorResponse = new HashMap<>();
   errorResponse.put("error", e.getLocalizedMessage());
   errorResponse.put("status", HttpStatus.BAD_REQUEST.value());
   return ResponseEntity.badRequest().body(errorResponse);
  }
  SecurityContextHolder.getContext().setAuthentication(authentication);
  UserDetails userDetails = (UserDetails) authentication.getPrincipal();
  String jwtToken = jwtUtils.generateTokenFromUsername(userDetails);
  List<String> roles = userDetails.getAuthorities().stream()
   .map(GrantedAuthority::getAuthority)
   .toList();
  return ResponseEntity.ok(new LoginResponseDto(userDetails.getUsername(), roles, jwtToken));
 }
}
