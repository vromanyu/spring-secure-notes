package com.vromanyu.secure.notes.repository;

import com.vromanyu.secure.notes.entity.AppRole;
import com.vromanyu.secure.notes.entity.AppRoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppRoleRepository extends JpaRepository<AppRole, Integer> {
 Optional<AppRole> findByRole(AppRoleEnum role);
}
