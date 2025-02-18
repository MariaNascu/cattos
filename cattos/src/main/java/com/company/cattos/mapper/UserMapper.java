package com.company.cattos.mapper;

import com.company.cattos.dto.UserDto;
import com.company.cattos.model.User;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User mapToUserDto(UserDto userDto);

    @InheritInverseConfiguration
    void mapToUser(UserDto userDto, @MappingTarget User user);
}
