package com.vromanyu.secure.notes.controller;

import com.vromanyu.secure.notes.dto.UserInfoResponseDto;
import com.vromanyu.secure.notes.entity.AppUser;
import com.vromanyu.secure.notes.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

 private final AppUserRepository appUserRepository;

 @GetMapping("/user")
 public ResponseEntity<?> getUserDetails(@AuthenticationPrincipal UserDetails userDetails){

  AppUser appUser = appUserRepository.findByUsername(userDetails.getUsername()).orElseThrow(() -> new RuntimeException("username not found"));

  List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
  UserInfoResponseDto responseDto = new UserInfoResponseDto(
   appUser.getId(), appUser.getUsername(), appUser.getEmail(), appUser.isNonLocked(), appUser.isNonExpired(),
   appUser.isCredentialsNonExpired(), appUser.isEnabled(), appUser.getCredentialsExpiryDate(), appUser.getAccountExpiryDate(),
   appUser.isTwoFactorEnabled(), roles);

  return ResponseEntity.ok(responseDto);
 }

 @GetMapping("/username")
 public ResponseEntity<String> getUsername(@AuthenticationPrincipal UserDetails userDetails){
  AppUser appUser = appUserRepository.findByUsername(userDetails.getUsername()).orElseThrow(() -> new RuntimeException("username not found"));
  return ResponseEntity.ok(appUser.getUsername());
 }
}
