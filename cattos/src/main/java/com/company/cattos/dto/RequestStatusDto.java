package com.company.cattos.dto;

import com.company.cattos.enums.RequestStatus;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class RequestStatusDto {
    RequestStatus status;
}
