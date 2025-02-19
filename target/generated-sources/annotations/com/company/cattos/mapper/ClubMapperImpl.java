package com.company.cattos.mapper;

import com.company.cattos.dto.ArticleDto;
import com.company.cattos.dto.ClubDto;
import com.company.cattos.dto.EventDto;
import com.company.cattos.dto.MemberDto;
import com.company.cattos.model.Article;
import com.company.cattos.model.Club;
import com.company.cattos.model.Event;
import com.company.cattos.model.Member;
import java.util.ArrayList;
import java.util.Arrays;
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
public class ClubMapperImpl implements ClubMapper {

    @Autowired
    private EventMapper eventMapper;
    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public ClubDto mapToClubDto(Club club) {
        if ( club == null ) {
            return null;
        }

        ClubDto clubDto = new ClubDto();

        clubDto.setUuid( club.getUuid() );
        clubDto.setName( club.getName() );
        byte[] avatar = club.getAvatar();
        if ( avatar != null ) {
            clubDto.setAvatar( Arrays.copyOf( avatar, avatar.length ) );
        }
        clubDto.setDescription( club.getDescription() );
        clubDto.setAddress( club.getAddress() );
        clubDto.setMembers( memberListToMemberDtoList( club.getMembers() ) );
        clubDto.setEvents( eventListToEventDtoList( club.getEvents() ) );
        clubDto.setArticles( articleListToArticleDtoList( club.getArticles() ) );

        return clubDto;
    }

    @Override
    public void mapToClub(ClubDto clubDTO, Club club) {
        if ( clubDTO == null ) {
            return;
        }

        club.setUuid( clubDTO.getUuid() );
        club.setName( clubDTO.getName() );
        byte[] avatar = clubDTO.getAvatar();
        if ( avatar != null ) {
            club.setAvatar( Arrays.copyOf( avatar, avatar.length ) );
        }
        else {
            club.setAvatar( null );
        }
        club.setDescription( clubDTO.getDescription() );
        club.setAddress( clubDTO.getAddress() );
        if ( club.getEvents() != null ) {
            List<Event> list = eventDtoListToEventList( clubDTO.getEvents() );
            if ( list != null ) {
                club.getEvents().clear();
                club.getEvents().addAll( list );
            }
            else {
                club.setEvents( null );
            }
        }
        else {
            List<Event> list = eventDtoListToEventList( clubDTO.getEvents() );
            if ( list != null ) {
                club.setEvents( list );
            }
        }
        if ( club.getMembers() != null ) {
            List<Member> list1 = memberDtoListToMemberList( clubDTO.getMembers() );
            if ( list1 != null ) {
                club.getMembers().clear();
                club.getMembers().addAll( list1 );
            }
            else {
                club.setMembers( null );
            }
        }
        else {
            List<Member> list1 = memberDtoListToMemberList( clubDTO.getMembers() );
            if ( list1 != null ) {
                club.setMembers( list1 );
            }
        }
        if ( club.getArticles() != null ) {
            List<Article> list2 = articleDtoListToArticleList( clubDTO.getArticles() );
            if ( list2 != null ) {
                club.getArticles().clear();
                club.getArticles().addAll( list2 );
            }
            else {
                club.setArticles( null );
            }
        }
        else {
            List<Article> list2 = articleDtoListToArticleList( clubDTO.getArticles() );
            if ( list2 != null ) {
                club.setArticles( list2 );
            }
        }
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

    protected List<EventDto> eventListToEventDtoList(List<Event> list) {
        if ( list == null ) {
            return null;
        }

        List<EventDto> list1 = new ArrayList<EventDto>( list.size() );
        for ( Event event : list ) {
            list1.add( eventMapper.mapToEventDto( event ) );
        }

        return list1;
    }

    protected List<ArticleDto> articleListToArticleDtoList(List<Article> list) {
        if ( list == null ) {
            return null;
        }

        List<ArticleDto> list1 = new ArrayList<ArticleDto>( list.size() );
        for ( Article article : list ) {
            list1.add( articleMapper.mapToArticleDto( article ) );
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

    protected Event eventDtoToEvent(EventDto eventDto) {
        if ( eventDto == null ) {
            return null;
        }

        Event event = new Event();

        event.setUuid( eventDto.getUuid() );
        event.setTitle( eventDto.getTitle() );
        event.setContent( eventDto.getContent() );
        event.setLocation( eventDto.getLocation() );
        event.setParticipants( memberDtoListToMemberList( eventDto.getParticipants() ) );

        return event;
    }

    protected List<Event> eventDtoListToEventList(List<EventDto> list) {
        if ( list == null ) {
            return null;
        }

        List<Event> list1 = new ArrayList<Event>( list.size() );
        for ( EventDto eventDto : list ) {
            list1.add( eventDtoToEvent( eventDto ) );
        }

        return list1;
    }

    protected Article articleDtoToArticle(ArticleDto articleDto) {
        if ( articleDto == null ) {
            return null;
        }

        Article article = new Article();

        article.setUuid( articleDto.getUuid() );
        article.setDate( articleDto.getDate() );
        article.setTitle( articleDto.getTitle() );
        article.setContent( articleDto.getContent() );

        return article;
    }

    protected List<Article> articleDtoListToArticleList(List<ArticleDto> list) {
        if ( list == null ) {
            return null;
        }

        List<Article> list1 = new ArrayList<Article>( list.size() );
        for ( ArticleDto articleDto : list ) {
            list1.add( articleDtoToArticle( articleDto ) );
        }

        return list1;
    }
}
