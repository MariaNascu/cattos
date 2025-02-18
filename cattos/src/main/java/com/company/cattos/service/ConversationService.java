package com.company.cattos.service;

import com.company.cattos.dto.ConversationDto;

import java.util.List;
import java.util.UUID;

public interface ConversationService {

    ConversationDto findConversationByUuid(UUID conversationUuid);

    void create(UUID clubUuid, UUID memberUuid, ConversationDto conversationDto);

    void update(UUID conversationUuid, ConversationDto conversationDto);

    List<ConversationDto> findAllFromClubByMemberUuid(UUID clubUuid, UUID memberuuid);

}
