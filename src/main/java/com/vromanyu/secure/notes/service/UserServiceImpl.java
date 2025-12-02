package com.vromanyu.secure.notes.service;

import com.vromanyu.secure.notes.dto.AppUserDto;
import com.vromanyu.secure.notes.entity.AppRole;
import com.vromanyu.secure.notes.entity.AppRoleEnum;
import com.vromanyu.secure.notes.entity.AppUser;
import com.vromanyu.secure.notes.repository.AppRoleRepository;
import com.vromanyu.secure.notes.repository.AppUserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

 private final AppUserRepository appUserRepository;
 private final AppRoleRepository appRoleRepository;

 @Override
 public void updateUserRole(Integer id, String roleName) {
  AppUser appUser = appUserRepository.findById(id).orElseThrow(() -> new RuntimeException("user was not found"));
  AppRoleEnum appRoleEnum = AppRoleEnum.valueOf(roleName);
  AppRole appRole = appRoleRepository.findByRole(appRoleEnum).orElseThrow(() -> new RuntimeException("role was not found"));
  appUser.setAppRole(appRole);
  appUserRepository.save(appUser);
 }

 @Override
 public List<AppUser> getAllUsers() {
  return appUserRepository.findAll();
 }

 @Override
 public AppUserDto getUserById(Integer id) {
  AppUser appUser = appUserRepository.findById(id).orElseThrow(() -> new RuntimeException("user was not found"));
  return convertToDto(appUser);
 }

 private static AppUserDto convertToDto(AppUser appUser) {
  return new AppUserDto(appUser.getId(),
   appUser.getUsername(),
   appUser.getEmail(),
   appUser.isNonLocked(),
   appUser.isNonExpired(),
   appUser.isCredentialsNonExpired(),
   appUser.isEnabled(),
   appUser.getCredentialsExpiryDate(),
   appUser.getAccountExpiryDate(),
   appUser.getTwoFactorSecret(),
   appUser.isTwoFactorEnabled(),
   appUser.getSignUpMethod(),
   appUser.getAppRole().getRole(),
   appUser.getCreationDate(),
   appUser.getUpdateDate());
 }
}
