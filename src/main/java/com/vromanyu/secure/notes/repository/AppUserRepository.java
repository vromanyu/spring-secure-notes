package com.vromanyu.secure.notes.repository;

import com.vromanyu.secure.notes.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser, Integer> {
 Optional<AppUser> findByUsername(String username);
 boolean existsByUsername(String username);
 boolean existsByEmail(String email);
}
