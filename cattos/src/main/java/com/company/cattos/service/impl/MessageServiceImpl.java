package com.company.cattos.service.impl;

import com.company.cattos.dto.ConversationDto;
import com.company.cattos.dto.MessageDto;
import com.company.cattos.exception.CattosException;
import com.company.cattos.mapper.ConversationMapper;
import com.company.cattos.mapper.MessageMapper;
import com.company.cattos.model.Conversation;
import com.company.cattos.model.Message;
import com.company.cattos.repository.ConversationRepository;
import com.company.cattos.repository.MessageRepository;
import com.company.cattos.service.ConversationService;
import com.company.cattos.service.MessageService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class MessageServiceImpl implements MessageService {

    private final ConversationService conversationService;
    private final ConversationRepository conversationRepository;
    private final MessageRepository messageRepository;
    private final MessageMapper messageMapper;
    private final ConversationMapper conversationMapper;

    @Override
    @Transactional
    public void addMessage(UUID conversationUUid, MessageDto messageDto) {
        Message message = new Message();
        messageMapper.mapToMessage(messageDto, message);
        Conversation conversation = new Conversation();
        ConversationDto conversationDto = conversationService.findConversationByUuid(conversationUUid);
        conversationMapper.mapToConversation(conversationDto, conversation);
        message.setMappedConversation(conversation);
//        conversation.addMessage(message);

        messageRepository.save(message);


    }

    @Override
    public void removeMessage(UUID messageUuid) {
        Message message = messageRepository.findMessageByUuid(messageUuid);

        if (message == null) {
            throw CattosException.of("The message with the uuid %s doesn't exist", messageUuid);
        } else {
            Conversation conversation = new Conversation();
            ConversationDto conversationDto = conversationService.findConversationByUuid(message.getConversation().getUuid());
            conversationMapper.mapToConversation(conversationDto, conversation);
//            conversation.removeMessage(message);
            conversationRepository.save(conversation);

            messageRepository.delete(message);
        }

    }

    public void updateMessage(UUID conversationUuid, UUID messageUuid, MessageDto messageDto) {
        Conversation conversation = new Conversation();
        ConversationDto conversationDto = conversationService.findConversationByUuid(conversationUuid);
        conversationMapper.mapToConversation(conversationDto, conversation);

        Message message = messageRepository.findMessageByUuid(messageUuid);

        if (conversation.getMessages().contains(message)) {
            messageMapper.mapToMessage(messageDto, message);
            messageRepository.save(message);
        }
        throw CattosException.of("The message doesn't belong to the conversation with UUID %s", conversationUuid);

    }
}
