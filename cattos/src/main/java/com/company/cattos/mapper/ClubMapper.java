package com.company.cattos.mapper;

import com.company.cattos.dto.ClubDto;
import com.company.cattos.model.Club;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring",
        uses = {EventMapper.class,
                MemberMapper.class,
                ArticleMapper.class})
public interface ClubMapper {

    ClubDto mapToClubDto(Club club);

    @InheritInverseConfiguration
    void mapToClub(ClubDto clubDTO, @MappingTarget Club club);
}
