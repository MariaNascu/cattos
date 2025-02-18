package com.company.cattos.service;

import com.company.cattos.dto.MessageDto;

import java.util.UUID;

public interface MessageService {

    void addMessage(UUID conversationUUid, MessageDto messageDto);
    void removeMessage(UUID messageUuid);
}
