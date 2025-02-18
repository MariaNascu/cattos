package com.company.cattos.dto;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@RequiredArgsConstructor
public class ConversationDto {
    private UUID uuid;
    private String name;
    private List<MessageDto> messages;
    private List<MemberDto> members;

}
