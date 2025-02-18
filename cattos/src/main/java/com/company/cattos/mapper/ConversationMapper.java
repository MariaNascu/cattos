package com.company.cattos.mapper;

import com.company.cattos.dto.ConversationDto;
import com.company.cattos.model.Conversation;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring",
        uses = {MemberMapper.class,
                MessageMapper.class})
public interface ConversationMapper {
    ConversationDto mapToConversationDto(Conversation conversation);

    @InheritInverseConfiguration
    void mapToConversation(ConversationDto conversationDto, @MappingTarget Conversation conversation);

}
