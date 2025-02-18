package com.company.cattos.mapper;

import com.company.cattos.dto.RequestDto;
import com.company.cattos.model.Request;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring",
        uses = {UserMapper.class,
                ClubMapper.class})
public interface RequestMapper {

    RequestDto mapToRequestDto(Request request);

    @InheritInverseConfiguration
    void mapToRequest(RequestDto requestDto, @MappingTarget Request request);

}
