package com.vromanyu.secure.notes.controller;

import com.vromanyu.secure.notes.config.security.jwt.JwtUtils;
import com.vromanyu.secure.notes.dto.LoginRequestDto;
import com.vromanyu.secure.notes.dto.LoginResponseDto;
import com.vromanyu.secure.notes.dto.MessageResponseDto;
import com.vromanyu.secure.notes.dto.SignupRequestDto;
import com.vromanyu.secure.notes.entity.AppRole;
import com.vromanyu.secure.notes.entity.AppRoleEnum;
import com.vromanyu.secure.notes.entity.AppUser;
import com.vromanyu.secure.notes.repository.AppRoleRepository;
import com.vromanyu.secure.notes.repository.AppUserRepository;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/public/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

 private final JwtUtils jwtUtils;
 private final AuthenticationManager authenticationManager;
 private final PasswordEncoder passwordEncoder;
 private final AppUserRepository appUserRepository;
 private final AppRoleRepository appRoleRepository;

 @PostMapping("/sign-in")
 public ResponseEntity<?> authenticateUser(@RequestBody LoginRequestDto loginRequest){
  Authentication authentication;
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

 @PostMapping("/sign-up")
 public ResponseEntity<?> registerUser(@RequestBody SignupRequestDto signupRequestDto) {
  if (appUserRepository.existsByUsername(signupRequestDto.username())) {
   return ResponseEntity.badRequest().body(new MessageResponseDto("username already exists"));
  }
  if(appUserRepository.existsByEmail(signupRequestDto.email())){
   return ResponseEntity.badRequest().body(new MessageResponseDto("email already exists"));
  }

  AppUser appUser = new AppUser();
  appUser.setUsername(signupRequestDto.username());
  appUser.setEmail(signupRequestDto.email());
  appUser.setPassword(passwordEncoder.encode(signupRequestDto.password()));

  String role = signupRequestDto.role();
  AppRole appRole;
  if (role != null){
  appRole = appRoleRepository.findByRole(AppRoleEnum.valueOf(role)).orElseThrow(() -> new RuntimeException("invalid role"));
  } else {
   appRole = appRoleRepository.findByRole(AppRoleEnum.ROLE_USER).orElseThrow(() -> new RuntimeException("invalid role"));
  }
  appUser.setAppRole(appRole);

  appUser.setNonLocked(true);
  appUser.setNonExpired(true);
  appUser.setCredentialsNonExpired(true);
  appUser.setEnabled(true);
  appUser.setCredentialsExpiryDate(LocalDate.now().plusYears(1));
  appUser.setAccountExpiryDate(LocalDate.now().plusYears(1));
  appUser.setTwoFactorEnabled(false);
  appUser.setSignUpMethod("email");

  appUserRepository.save(appUser);
  return ResponseEntity.ok(new MessageResponseDto("user registered successfully"));
 }
}
