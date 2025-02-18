package com.company.cattos.service;

import java.util.UUID;

public interface ConversationMessageService {
    void delete(UUID conversationUuid, UUID clubUuid);
}
