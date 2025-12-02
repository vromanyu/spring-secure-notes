package com.vromanyu.secure.notes;

import com.vromanyu.secure.notes.entity.AppRole;
import com.vromanyu.secure.notes.entity.AppRoleEnum;
import com.vromanyu.secure.notes.entity.AppUser;
import com.vromanyu.secure.notes.repository.AppRoleRepository;
import com.vromanyu.secure.notes.repository.AppUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class SecureNotesApplication {

 public static void main(String[] args) {
  SpringApplication.run(SecureNotesApplication.class, args);
 }

 @Bean
 public CommandLineRunner commandLineRunner(AppRoleRepository appRoleRepository, AppUserRepository appUserRepository, PasswordEncoder passwordEncoder) {
  return args -> {
   AppRole userRole = appRoleRepository.findByRole(AppRoleEnum.ROLE_USER).orElseGet(() -> appRoleRepository.save(new AppRole(AppRoleEnum.ROLE_USER)));
   AppRole adminRole = appRoleRepository.findByRole(AppRoleEnum.ROLE_ADMIN).orElseGet(() -> appRoleRepository.save(new AppRole(AppRoleEnum.ROLE_ADMIN)));

   generateUser(appUserRepository, passwordEncoder,  "user", "user@gmail.com", userRole);
   generateUser(appUserRepository, passwordEncoder,"admin", "admin@gmail.com", adminRole);
  };
 }

 private static void generateUser(AppUserRepository appUserRepository, PasswordEncoder passwordEncoder, String username, String email, AppRole userRole) {
  if (!appUserRepository.existsByUsername(username)) {
   AppUser appUser = new AppUser();
   appUser.setUsername(username);
   appUser.setEmail(email);
   appUser.setPassword(passwordEncoder.encode("1234"));
   appUser.setNonLocked(false);
   appUser.setNonExpired(true);
   appUser.setCredentialsNonExpired(true);
   appUser.setEnabled(true);
   appUser.setTwoFactorEnabled(false);
   appUser.setSignUpMethod("email");
   appUser.setAppRole(userRole);
   appUserRepository.save(appUser);
  }
 }

 @Bean
 public PasswordEncoder passwordEncoder() {
  return PasswordEncoderFactories.createDelegatingPasswordEncoder();
 }
}
