package com.company.cattos.dto;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@RequiredArgsConstructor
public class EventDto {
    private UUID uuid;
    private String title;
    private String content;
    private String location;
    private List<MemberDto> participants;
    private UUID clubUuid;
}
