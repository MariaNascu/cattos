package com.company.cattos.mapper;

import com.company.cattos.dto.UserDto;
import com.company.cattos.model.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-02-19T17:54:39+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.7 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User mapToUserDto(UserDto userDto) {
        if ( userDto == null ) {
            return null;
        }

        User user = new User();

        user.setUuid( userDto.getUuid() );
        user.setFirstName( userDto.getFirstName() );
        user.setLastName( userDto.getLastName() );

        return user;
    }

    @Override
    public void mapToUser(UserDto userDto, User user) {
        if ( userDto == null ) {
            return;
        }

        user.setUuid( userDto.getUuid() );
        user.setFirstName( userDto.getFirstName() );
        user.setLastName( userDto.getLastName() );
    }
}
