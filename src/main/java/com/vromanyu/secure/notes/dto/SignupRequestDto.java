package com.vromanyu.secure.notes.dto;

public record SignupRequestDto(
 String username,
 String email,
 String password,
 String role
) {
}
