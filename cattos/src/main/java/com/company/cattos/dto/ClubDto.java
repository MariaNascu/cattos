package com.company.cattos.dto;

import lombok.*;

import java.util.List;
import java.util.UUID;


@Getter
@Setter
@RequiredArgsConstructor
public class ClubDto {

    private UUID uuid;
    private String name;
    private byte[] avatar;
    private String description;
    private String address;
    private List<MemberDto> members;
    private List<EventDto> events;
    private List<ArticleDto> articles;

}
