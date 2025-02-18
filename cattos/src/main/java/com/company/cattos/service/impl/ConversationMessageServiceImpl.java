package com.company.cattos.service.impl;

import com.company.cattos.dto.ClubDto;
import com.company.cattos.model.Conversation;
import com.company.cattos.model.Message;
import com.company.cattos.repository.ConversationRepository;
import com.company.cattos.service.ClubService;
import com.company.cattos.service.ConversationMessageService;
import com.company.cattos.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ConversationMessageServiceImpl implements ConversationMessageService {
    private final ConversationRepository conversationRepository;
    private final ClubService clubService;
    private final MessageService messageService;

    @Override
    public void delete(UUID conversationUuid, UUID clubUuid) {
        ClubDto club = clubService.findClubByUuid(clubUuid);
        Conversation conversation = conversationRepository.findConversationByUuid(conversationUuid);
        boolean isConversationInClub = club.getMembers().stream()
                .anyMatch(b -> b.getConversationUuids().toString().equals(conversationUuid.toString()));
        if (isConversationInClub) {
            for (Message message : conversation.getMessages()) {
                messageService.removeMessage(message.getUuid());
            }
            conversationRepository.delete(conversation);
        }
    }
}
