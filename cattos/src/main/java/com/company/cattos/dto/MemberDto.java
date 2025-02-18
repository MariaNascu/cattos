package com.company.cattos.dto;

import com.company.cattos.enums.Role;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@RequiredArgsConstructor
public class MemberDto {
    private UUID uuid;
    private String username;
    private Role role;
    private Integer userId;
    private ArticleDto article;
    private ClubDto clubDto;
    private List<EventDto> eventUuids;
    private List<ConversationDto> conversationUuids;

}
