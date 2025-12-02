package com.vromanyu.secure.notes.controller;

import com.vromanyu.secure.notes.dto.AppUserDto;
import com.vromanyu.secure.notes.entity.AppUser;
import com.vromanyu.secure.notes.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {

 private final UserService userService;

 @GetMapping("/users")
 public ResponseEntity<List<AppUser>> getAllUsers(){
  return ResponseEntity.ok(userService.getAllUsers());
 }

 @GetMapping("/users/{id}")
 public ResponseEntity<AppUserDto> getUserById(@PathVariable Integer id) {
  return ResponseEntity.ok(userService.getUserById(id));
 }

 @PutMapping("/update-role")
 public ResponseEntity<String> updateUserRole(@RequestParam Integer id, @RequestParam String role) {
  userService.updateUserRole(id, role);
  return ResponseEntity.ok("user role updated successfully");
 }

}
