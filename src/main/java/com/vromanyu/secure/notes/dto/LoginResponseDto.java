package com.vromanyu.secure.notes.dto;

import java.util.List;

public record LoginResponseDto(String username, List<String> roles, String token) {
}
