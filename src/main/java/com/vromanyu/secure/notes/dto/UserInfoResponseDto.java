package com.vromanyu.secure.notes.dto;

import java.time.LocalDate;
import java.util.List;

public record UserInfoResponseDto(
 Integer id,
 String username,
 String email,
 boolean accountNonLocked,
 boolean accountNonExpired,
 boolean credentialsNonExpired,
 boolean enabled,
 LocalDate credentialsExpiryDate,
 LocalDate accountExpiryDate,
 boolean isTwoFactorEnabled,
 List<String> roles
) {
}
