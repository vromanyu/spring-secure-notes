package com.vromanyu.secure.notes.config.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vromanyu.secure.notes.entity.AppUser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserDetailsImpl implements UserDetails {

 private Integer id;
 private String username;
 private String email;
 @JsonIgnore
 private String password;
 private boolean is2faEnabled;
 private Collection<? extends GrantedAuthority> authorities;

 public UserDetailsImpl(Integer id, String username, String email, String password, boolean is2faEnabled, Collection<? extends GrantedAuthority> authorities) {
  this.id = id;
  this.username = username;
  this.email = email;
  this.password = password;
  this.is2faEnabled = is2faEnabled;
  this.authorities = authorities;
 }

 public static UserDetailsImpl build(AppUser appUser) {
  GrantedAuthority authority = new SimpleGrantedAuthority(appUser.getAppRole().getRole().toString());

  return new UserDetailsImpl(appUser.getId(),
   appUser.getUsername(),
   appUser.getEmail(),
   appUser.getPassword(),
   appUser.isTwoFactorEnabled(),
   List.of(authority));
 }

 @Override
 public Collection<? extends GrantedAuthority> getAuthorities() {
  return authorities;
 }

 @Override
 public String getPassword() {
  return password;
 }

 @Override
 public String getUsername() {
  return username;
 }

 @Override
 public boolean equals(Object o) {
  if (!(o instanceof UserDetailsImpl that)) return false;
  return Objects.equals(getId(), that.getId());
 }

 @Override
 public int hashCode() {
  return Objects.hashCode(getId());
 }
}
