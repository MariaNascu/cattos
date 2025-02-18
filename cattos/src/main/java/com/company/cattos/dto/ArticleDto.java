package com.company.cattos.dto;

import lombok.*;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@RequiredArgsConstructor
public class ArticleDto {
    private UUID uuid;
    private Date date;
    private String title;
    private String content;
    private UUID writerUuid;
    private UUID clubUuid;


}
