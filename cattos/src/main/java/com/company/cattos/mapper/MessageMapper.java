package com.company.cattos.mapper;

import com.company.cattos.dto.MessageDto;
import com.company.cattos.model.Message;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring",
        uses = {MemberMapper.class})
public interface MessageMapper {
    MessageDto mapToMessageDto(Message message);

    @InheritInverseConfiguration
    void mapToMessage(MessageDto messageDto, @MappingTarget Message message);

}
