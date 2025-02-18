package com.company.cattos.mapper;

import com.company.cattos.dto.ArticleDto;
import com.company.cattos.model.Article;
import com.company.cattos.model.Club;
import com.company.cattos.model.Member;
import com.company.cattos.repository.ClubRepository;
import com.company.cattos.repository.MemberRepository;
import lombok.*;
import org.mapstruct.*;

import java.util.UUID;

@NoArgsConstructor(force = true)
@AllArgsConstructor
@Getter
@Setter
@Mapper(componentModel = "spring",
        uses = {ClubMapper.class,
                MemberMapper.class})
public abstract class ArticleMapper {

    private final MemberRepository memberRepository;
    private final ClubRepository clubRepository;

    @Mappings(value = {
            @Mapping(source = "club.uuid", target = "clubUuid"),
            @Mapping(source = "writer.uuid", target = "writerUuid")
    })
    public abstract ArticleDto mapToArticleDto(Article article);

    @Mappings(value = {
            @Mapping(source = "clubUuid", target = "club", qualifiedByName = "uuidToClub"),
            @Mapping(source = "writerUuid", target = "writer", qualifiedByName = "uuidToMember")
    })
    @InheritInverseConfiguration
    public abstract void mapToArticle(ArticleDto articleDto, @MappingTarget Article article);


    @Named("uuidToClub")
    public Club uuidToClub(UUID uuid) {
        return clubRepository.findClubByUuid(uuid);
    }

    @Named("uuidToMember")
    public Member uuidToMember(UUID uuid) {
        return memberRepository.findByUuid(uuid);
    }
}
