package com.company.cattos.security.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class UserDto {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String phone;
    private String role;
}
