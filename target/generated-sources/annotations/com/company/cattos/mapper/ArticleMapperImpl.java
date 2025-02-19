package com.company.cattos.mapper;

import com.company.cattos.dto.ArticleDto;
import com.company.cattos.model.Article;
import com.company.cattos.model.Club;
import com.company.cattos.model.Member;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-02-19T17:54:39+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.7 (Oracle Corporation)"
)
@Component
public class ArticleMapperImpl extends ArticleMapper {

    @Override
    public ArticleDto mapToArticleDto(Article article) {
        if ( article == null ) {
            return null;
        }

        ArticleDto articleDto = new ArticleDto();

        articleDto.setClubUuid( articleClubUuid( article ) );
        articleDto.setWriterUuid( articleWriterUuid( article ) );
        articleDto.setUuid( article.getUuid() );
        articleDto.setDate( article.getDate() );
        articleDto.setTitle( article.getTitle() );
        articleDto.setContent( article.getContent() );

        return articleDto;
    }

    @Override
    public void mapToArticle(ArticleDto articleDto, Article article) {
        if ( articleDto == null ) {
            return;
        }

        article.setClub( uuidToClub( articleDto.getClubUuid() ) );
        article.setWriter( uuidToMember( articleDto.getWriterUuid() ) );
        article.setUuid( articleDto.getUuid() );
        article.setDate( articleDto.getDate() );
        article.setTitle( articleDto.getTitle() );
        article.setContent( articleDto.getContent() );
    }

    private UUID articleClubUuid(Article article) {
        if ( article == null ) {
            return null;
        }
        Club club = article.getClub();
        if ( club == null ) {
            return null;
        }
        UUID uuid = club.getUuid();
        if ( uuid == null ) {
            return null;
        }
        return uuid;
    }

    private UUID articleWriterUuid(Article article) {
        if ( article == null ) {
            return null;
        }
        Member writer = article.getWriter();
        if ( writer == null ) {
            return null;
        }
        UUID uuid = writer.getUuid();
        if ( uuid == null ) {
            return null;
        }
        return uuid;
    }
}
