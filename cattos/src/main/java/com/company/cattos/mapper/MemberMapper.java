package com.company.cattos.mapper;

import com.company.cattos.dto.MemberDto;
import com.company.cattos.model.Member;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring",
        uses = {EventMapper.class,
                MessageMapper.class,
                ArticleMapper.class,
                ClubMapper.class})
public interface MemberMapper {
    MemberDto mapToMemberDto(Member member);

    @InheritInverseConfiguration
    void mapToMember(MemberDto memberDto, @MappingTarget Member member);
}
