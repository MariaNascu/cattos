package com.company.cattos.mapper;

import com.company.cattos.dto.ConversationDto;
import com.company.cattos.dto.MemberDto;
import com.company.cattos.dto.MessageDto;
import com.company.cattos.model.Conversation;
import com.company.cattos.model.Member;
import com.company.cattos.model.Message;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-02-19T17:54:38+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.7 (Oracle Corporation)"
)
@Component
public class MessageMapperImpl implements MessageMapper {

    @Autowired
    private MemberMapper memberMapper;

    @Override
    public MessageDto mapToMessageDto(Message message) {
        if ( message == null ) {
            return null;
        }

        MessageDto messageDto = new MessageDto();

        messageDto.setUuid( message.getUuid() );
        messageDto.setContent( message.getContent() );
        messageDto.setSentDate( message.getSentDate() );
        messageDto.setUpdatedDate( message.getUpdatedDate() );
        messageDto.setMember( memberMapper.mapToMemberDto( message.getMember() ) );
        messageDto.setConversation( conversationToConversationDto( message.getConversation() ) );

        return messageDto;
    }

    @Override
    public void mapToMessage(MessageDto messageDto, Message message) {
        if ( messageDto == null ) {
            return;
        }

        message.setUuid( messageDto.getUuid() );
        message.setContent( messageDto.getContent() );
        message.setSentDate( messageDto.getSentDate() );
        message.setUpdatedDate( messageDto.getUpdatedDate() );
        if ( messageDto.getMember() != null ) {
            if ( message.getMember() == null ) {
                message.setMember( new Member() );
            }
            memberMapper.mapToMember( messageDto.getMember(), message.getMember() );
        }
        else {
            message.setMember( null );
        }
        if ( messageDto.getConversation() != null ) {
            if ( message.getConversation() == null ) {
                message.setConversation( new Conversation() );
            }
            conversationDtoToConversation( messageDto.getConversation(), message.getConversation() );
        }
        else {
            message.setConversation( null );
        }
    }

    protected List<MessageDto> messageListToMessageDtoList(List<Message> list) {
        if ( list == null ) {
            return null;
        }

        List<MessageDto> list1 = new ArrayList<MessageDto>( list.size() );
        for ( Message message : list ) {
            list1.add( mapToMessageDto( message ) );
        }

        return list1;
    }

    protected List<MemberDto> memberListToMemberDtoList(List<Member> list) {
        if ( list == null ) {
            return null;
        }

        List<MemberDto> list1 = new ArrayList<MemberDto>( list.size() );
        for ( Member member : list ) {
            list1.add( memberMapper.mapToMemberDto( member ) );
        }

        return list1;
    }

    protected ConversationDto conversationToConversationDto(Conversation conversation) {
        if ( conversation == null ) {
            return null;
        }

        ConversationDto conversationDto = new ConversationDto();

        conversationDto.setUuid( conversation.getUuid() );
        conversationDto.setName( conversation.getName() );
        conversationDto.setMessages( messageListToMessageDtoList( conversation.getMessages() ) );
        conversationDto.setMembers( memberListToMemberDtoList( conversation.getMembers() ) );

        return conversationDto;
    }

    protected Member memberDtoToMember(MemberDto memberDto) {
        if ( memberDto == null ) {
            return null;
        }

        Member member = new Member();

        member.setUuid( memberDto.getUuid() );
        member.setUsername( memberDto.getUsername() );
        member.setRole( memberDto.getRole() );

        return member;
    }

    protected List<Message> messageDtoListToMessageList(List<MessageDto> list) {
        if ( list == null ) {
            return null;
        }

        List<Message> list1 = new ArrayList<Message>( list.size() );
        for ( MessageDto messageDto : list ) {
            list1.add( messageDtoToMessage( messageDto ) );
        }

        return list1;
    }

    protected List<Member> memberDtoListToMemberList(List<MemberDto> list) {
        if ( list == null ) {
            return null;
        }

        List<Member> list1 = new ArrayList<Member>( list.size() );
        for ( MemberDto memberDto : list ) {
            list1.add( memberDtoToMember( memberDto ) );
        }

        return list1;
    }

    protected Conversation conversationDtoToConversation(ConversationDto conversationDto) {
        if ( conversationDto == null ) {
            return null;
        }

        Conversation conversation = new Conversation();

        conversation.setUuid( conversationDto.getUuid() );
        conversation.setName( conversationDto.getName() );
        conversation.setMessages( messageDtoListToMessageList( conversationDto.getMessages() ) );
        conversation.setMembers( memberDtoListToMemberList( conversationDto.getMembers() ) );

        return conversation;
    }

    protected Message messageDtoToMessage(MessageDto messageDto) {
        if ( messageDto == null ) {
            return null;
        }

        Message message = new Message();

        message.setUuid( messageDto.getUuid() );
        message.setContent( messageDto.getContent() );
        message.setSentDate( messageDto.getSentDate() );
        message.setUpdatedDate( messageDto.getUpdatedDate() );
        message.setMember( memberDtoToMember( messageDto.getMember() ) );
        message.setConversation( conversationDtoToConversation( messageDto.getConversation() ) );

        return message;
    }

    protected void conversationDtoToConversation(ConversationDto conversationDto, Conversation mappingTarget) {
        if ( conversationDto == null ) {
            return;
        }

        mappingTarget.setUuid( conversationDto.getUuid() );
        mappingTarget.setName( conversationDto.getName() );
        if ( mappingTarget.getMessages() != null ) {
            List<Message> list = messageDtoListToMessageList( conversationDto.getMessages() );
            if ( list != null ) {
                mappingTarget.getMessages().clear();
                mappingTarget.getMessages().addAll( list );
            }
            else {
                mappingTarget.setMessages( null );
            }
        }
        else {
            List<Message> list = messageDtoListToMessageList( conversationDto.getMessages() );
            if ( list != null ) {
                mappingTarget.setMessages( list );
            }
        }
        if ( mappingTarget.getMembers() != null ) {
            List<Member> list1 = memberDtoListToMemberList( conversationDto.getMembers() );
            if ( list1 != null ) {
                mappingTarget.getMembers().clear();
                mappingTarget.getMembers().addAll( list1 );
            }
            else {
                mappingTarget.setMembers( null );
            }
        }
        else {
            List<Member> list1 = memberDtoListToMemberList( conversationDto.getMembers() );
            if ( list1 != null ) {
                mappingTarget.setMembers( list1 );
            }
        }
    }
}
