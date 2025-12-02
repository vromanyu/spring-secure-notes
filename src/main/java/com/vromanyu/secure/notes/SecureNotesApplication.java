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

@SpringBootApplication
public class SecureNotesApplication {

 public static void main(String[] args) {
  SpringApplication.run(SecureNotesApplication.class, args);
 }

 @Bean
 public CommandLineRunner commandLineRunner(AppRoleRepository appRoleRepository, AppUserRepository appUserRepository) {
  return args -> {
   AppRole userRole = appRoleRepository.findByRole(AppRoleEnum.ROLE_USER).orElseGet(() -> appRoleRepository.save(new AppRole(AppRoleEnum.ROLE_USER)));
   AppRole adminRole = appRoleRepository.findByRole(AppRoleEnum.ROLE_ADMIN).orElseGet(() -> appRoleRepository.save(new AppRole(AppRoleEnum.ROLE_ADMIN)));

    if (!appUserRepository.existsByUsername("user")) {
     AppUser appUser = new AppUser();
     appUser.setUsername("user");
     appUser.setEmail("user@gmail.com");
     appUser.setPassword("{noop}1234");
     appUser.setNonLocked(false);
     appUser.setNonExpired(true);
     appUser.setCredentialsNonExpired(true);
     appUser.setEnabled(true);
     appUser.setTwoFactorEnabled(false);
     appUser.setSignUpMethod("email");
     appUser.setAppRole(userRole);
     userRole.getUsers().add(appUser);
     appUserRepository.save(appUser);
    }

   if (!appUserRepository.existsByUsername("admin")) {
    AppUser adminUser = new AppUser();
    adminUser.setUsername("admin");
    adminUser.setEmail("admin@gmail.com");
    adminUser.setPassword("{noop}1234");
    adminUser.setNonLocked(false);
    adminUser.setNonExpired(true);
    adminUser.setCredentialsNonExpired(true);
    adminUser.setEnabled(true);
    adminUser.setTwoFactorEnabled(false);
    adminUser.setSignUpMethod("email");
    adminUser.setAppRole(adminRole);
    adminRole.getUsers().add(adminUser);
    appUserRepository.save(adminUser);
   }
  };
 }

}
