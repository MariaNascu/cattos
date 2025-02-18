package com.company.cattos.dto;

import com.company.cattos.enums.RequestStatus;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@RequiredArgsConstructor
public class RequestDto {
    private UUID uuid;
    private ClubDto club;
    private RequestStatus status;
    private UserDto user;

}
