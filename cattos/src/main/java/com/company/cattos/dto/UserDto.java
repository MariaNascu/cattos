package com.company.cattos.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@RequiredArgsConstructor
public class UserDto {
    private UUID uuid;
    private String firstName;
    private String lastName;

}
