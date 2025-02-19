package com.company.cattos.mapper;

import com.company.cattos.dto.RequestDto;
import com.company.cattos.dto.UserDto;
import com.company.cattos.model.Club;
import com.company.cattos.model.Request;
import com.company.cattos.model.User;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-02-19T17:54:39+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.7 (Oracle Corporation)"
)
@Component
public class RequestMapperImpl implements RequestMapper {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ClubMapper clubMapper;

    @Override
    public RequestDto mapToRequestDto(Request request) {
        if ( request == null ) {
            return null;
        }

        RequestDto requestDto = new RequestDto();

        requestDto.setUuid( request.getUuid() );
        requestDto.setClub( clubMapper.mapToClubDto( request.getClub() ) );
        requestDto.setStatus( request.getStatus() );
        requestDto.setUser( userToUserDto( request.getUser() ) );

        return requestDto;
    }

    @Override
    public void mapToRequest(RequestDto requestDto, Request request) {
        if ( requestDto == null ) {
            return;
        }

        request.setUuid( requestDto.getUuid() );
        request.setStatus( requestDto.getStatus() );
        if ( requestDto.getClub() != null ) {
            if ( request.getClub() == null ) {
                request.setClub( new Club() );
            }
            clubMapper.mapToClub( requestDto.getClub(), request.getClub() );
        }
        else {
            request.setClub( null );
        }
        if ( requestDto.getUser() != null ) {
            if ( request.getUser() == null ) {
                request.setUser( new User() );
            }
            userMapper.mapToUser( requestDto.getUser(), request.getUser() );
        }
        else {
            request.setUser( null );
        }
    }

    protected UserDto userToUserDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserDto userDto = new UserDto();

        userDto.setUuid( user.getUuid() );
        userDto.setFirstName( user.getFirstName() );
        userDto.setLastName( user.getLastName() );

        return userDto;
    }
}
