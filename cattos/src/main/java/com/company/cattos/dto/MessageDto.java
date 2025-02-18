package com.company.cattos.dto;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@RequiredArgsConstructor
public class MessageDto {
    private UUID uuid;
    private String content;

    @CreationTimestamp
    private Date sentDate;

    @UpdateTimestamp
    private Date updatedDate;

    private MemberDto member;

    private ConversationDto conversation;

}
