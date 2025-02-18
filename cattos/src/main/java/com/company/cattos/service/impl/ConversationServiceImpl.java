package com.company.cattos.service.impl;

import com.company.cattos.dto.ClubDto;
import com.company.cattos.dto.ConversationDto;
import com.company.cattos.dto.MessageDto;
import com.company.cattos.exception.CattosException;
import com.company.cattos.mapper.ConversationMapper;
import com.company.cattos.mapper.MessageMapper;
import com.company.cattos.model.Conversation;
import com.company.cattos.model.Message;
import com.company.cattos.repository.ConversationRepository;
import com.company.cattos.service.ClubService;
import com.company.cattos.service.ConversationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ConversationServiceImpl implements ConversationService {

    private final ConversationRepository conversationRepository;
    private final ClubService clubService;
    private final ConversationMapper conversationMapper;
    private final MessageMapper messageMapper;


    @Override
    public void create(UUID clubUuid, UUID memberUuid, ConversationDto conversationDto) {
        ClubDto clubDto = clubService.findClubByUuid(clubUuid);

        boolean isMember = clubDto.getMembers().stream().anyMatch(m -> m.getUuid().toString().equals(memberUuid.toString()));

        if (isMember) {
            Conversation conversation = new Conversation();

            Message message = new Message();
            MessageDto messageDto = conversationDto.getMessages().get(0);

            messageMapper.mapToMessage(messageDto, message);
            conversationMapper.mapToConversation(conversationDto, conversation);

//            conversation.addMessage(message);
//            conversationRepository.save(conversation);
        }

        throw CattosException.of("The user with uuid %s is not a member of the club with uuid %s", memberUuid, clubUuid);
    }

    @Override
    public ConversationDto findConversationByUuid(UUID conversationUuid) {
        Conversation conversation = conversationRepository.findConversationByUuid(conversationUuid);
        return conversationMapper.mapToConversationDto(conversation);
    }

    @Override
    public void update(UUID conversationUuid, ConversationDto conversationDto) {
        Conversation conversation = conversationRepository.findConversationByUuid(conversationUuid);

        if (conversation != null) {
            conversationMapper.mapToConversation(conversationDto, conversation);
            conversationRepository.save(conversation);
        }
        throw CattosException.of("The conversation with UUID %s doesn't exist", conversationUuid);

    }

    @Override
    public List<ConversationDto> findAllFromClubByMemberUuid(UUID clubUuid, UUID memberuuid) {
        List<Conversation> list = conversationRepository.findAllConversationsByClubUuidAndMemberUuid(memberuuid, clubUuid);
        List<ConversationDto> dtoList = new ArrayList<>();

        for (Conversation c : list) {
            var dto = conversationMapper.mapToConversationDto(c);
            dtoList.add(dto);
        }

        return dtoList;
    }

}
