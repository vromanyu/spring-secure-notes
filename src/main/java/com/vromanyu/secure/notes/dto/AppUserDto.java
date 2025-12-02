package com.vromanyu.secure.notes.dto;

import com.vromanyu.secure.notes.entity.AppRoleEnum;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record AppUserDto(
 Integer id,
 String username,
 String email,
 boolean accountNonBlocked,
 boolean accountNonExpired,
 boolean credentialsNonExpired,
 boolean enabled,
 LocalDate credentialsExpiryDate,
 LocalDate accountExpiryDate,
 String twoFactorSecret,
 boolean isTwoFactorEnabled,
 String signUpMethod,
 AppRoleEnum appRole,
 LocalDateTime createdAt,
 LocalDateTime updatedAt
) {
}
