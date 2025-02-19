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
    date = "2025-02-19T17:54:39+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.7 (Oracle Corporation)"
)
@Component
public class ConversationMapperImpl implements ConversationMapper {

    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private MessageMapper messageMapper;

    @Override
    public ConversationDto mapToConversationDto(Conversation conversation) {
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

    @Override
    public void mapToConversation(ConversationDto conversationDto, Conversation conversation) {
        if ( conversationDto == null ) {
            return;
        }

        conversation.setUuid( conversationDto.getUuid() );
        conversation.setName( conversationDto.getName() );
        if ( conversation.getMessages() != null ) {
            List<Message> list = messageDtoListToMessageList( conversationDto.getMessages() );
            if ( list != null ) {
                conversation.getMessages().clear();
                conversation.getMessages().addAll( list );
            }
            else {
                conversation.setMessages( null );
            }
        }
        else {
            List<Message> list = messageDtoListToMessageList( conversationDto.getMessages() );
            if ( list != null ) {
                conversation.setMessages( list );
            }
        }
        if ( conversation.getMembers() != null ) {
            List<Member> list1 = memberDtoListToMemberList( conversationDto.getMembers() );
            if ( list1 != null ) {
                conversation.getMembers().clear();
                conversation.getMembers().addAll( list1 );
            }
            else {
                conversation.setMembers( null );
            }
        }
        else {
            List<Member> list1 = memberDtoListToMemberList( conversationDto.getMembers() );
            if ( list1 != null ) {
                conversation.setMembers( list1 );
            }
        }
    }

    protected List<MessageDto> messageListToMessageDtoList(List<Message> list) {
        if ( list == null ) {
            return null;
        }

        List<MessageDto> list1 = new ArrayList<MessageDto>( list.size() );
        for ( Message message : list ) {
            list1.add( messageMapper.mapToMessageDto( message ) );
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
}
