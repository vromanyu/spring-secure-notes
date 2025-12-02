package com.vromanyu.secure.notes.service;

import com.vromanyu.secure.notes.dto.AppUserDto;
import com.vromanyu.secure.notes.entity.AppUser;

import java.util.List;

public interface UserService {
 void updateUserRole(Integer id, String roleName);
 List<AppUser> getAllUsers();
 AppUserDto getUserById(Integer id);
}
