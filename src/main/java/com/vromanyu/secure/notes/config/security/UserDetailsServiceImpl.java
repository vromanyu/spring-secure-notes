package com.vromanyu.secure.notes.config.security;

import com.vromanyu.secure.notes.repository.AppUserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

 private final AppUserRepository appUserRepository;

 @Override
 public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
  return appUserRepository.findByUsername(username).map(UserDetailsImpl::build).orElseThrow(() -> new UsernameNotFoundException(username));
 }
}
