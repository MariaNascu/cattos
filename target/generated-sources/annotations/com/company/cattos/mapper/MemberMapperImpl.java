package com.company.cattos.mapper;

import com.company.cattos.dto.MemberDto;
import com.company.cattos.model.Member;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-02-19T17:54:39+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.7 (Oracle Corporation)"
)
@Component
public class MemberMapperImpl implements MemberMapper {

    @Override
    public MemberDto mapToMemberDto(Member member) {
        if ( member == null ) {
            return null;
        }

        MemberDto memberDto = new MemberDto();

        memberDto.setUuid( member.getUuid() );
        memberDto.setUsername( member.getUsername() );
        memberDto.setRole( member.getRole() );

        return memberDto;
    }

    @Override
    public void mapToMember(MemberDto memberDto, Member member) {
        if ( memberDto == null ) {
            return;
        }

        member.setUuid( memberDto.getUuid() );
        member.setUsername( memberDto.getUsername() );
        member.setRole( memberDto.getRole() );
    }
}
