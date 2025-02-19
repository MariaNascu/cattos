package com.company.cattos.mapper;

import com.company.cattos.dto.EventDto;
import com.company.cattos.dto.MemberDto;
import com.company.cattos.model.Club;
import com.company.cattos.model.Event;
import com.company.cattos.model.Member;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-02-19T17:54:39+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.7 (Oracle Corporation)"
)
@Component
public class EventMapperImpl extends EventMapper {

    @Autowired
    private MemberMapper memberMapper;

    @Override
    public EventDto mapToEventDto(Event event) {
        if ( event == null ) {
            return null;
        }

        EventDto eventDto = new EventDto();

        eventDto.setClubUuid( eventClubUuid( event ) );
        eventDto.setUuid( event.getUuid() );
        eventDto.setTitle( event.getTitle() );
        eventDto.setContent( event.getContent() );
        eventDto.setLocation( event.getLocation() );
        eventDto.setParticipants( memberListToMemberDtoList( event.getParticipants() ) );

        return eventDto;
    }

    @Override
    public void mapToEvent(EventDto eventDto, Event event) {
        if ( eventDto == null ) {
            return;
        }

        event.setClub( uuidToClub( eventDto.getClubUuid() ) );
        event.setUuid( eventDto.getUuid() );
        event.setTitle( eventDto.getTitle() );
        event.setContent( eventDto.getContent() );
        event.setLocation( eventDto.getLocation() );
        if ( event.getParticipants() != null ) {
            List<Member> list = memberDtoListToMemberList( eventDto.getParticipants() );
            if ( list != null ) {
                event.getParticipants().clear();
                event.getParticipants().addAll( list );
            }
            else {
                event.setParticipants( null );
            }
        }
        else {
            List<Member> list = memberDtoListToMemberList( eventDto.getParticipants() );
            if ( list != null ) {
                event.setParticipants( list );
            }
        }
    }

    private UUID eventClubUuid(Event event) {
        if ( event == null ) {
            return null;
        }
        Club club = event.getClub();
        if ( club == null ) {
            return null;
        }
        UUID uuid = club.getUuid();
        if ( uuid == null ) {
            return null;
        }
        return uuid;
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
}
