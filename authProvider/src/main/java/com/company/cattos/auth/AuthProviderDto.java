package com.company.cattos.auth;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@RequiredArgsConstructor
public class AuthProviderDto {

    private UUID uuid;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private String role;
}
