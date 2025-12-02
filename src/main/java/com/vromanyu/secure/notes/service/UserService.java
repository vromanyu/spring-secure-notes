package com.vromanyu.secure.notes.service;

import com.vromanyu.secure.notes.dto.AppUserDto;
import com.vromanyu.secure.notes.entity.AppUser;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface UserService {
 @PreAuthorize("hasRole('ADMIN')")
 void updateUserRole(Integer id, String roleName);
 @PreAuthorize("hasRole('ADMIN')")
 List<AppUser> getAllUsers();
 @PreAuthorize("hasRole('ADMIN')")
 AppUserDto getUserById(Integer id);
}
