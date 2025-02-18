package com.company.cattos.service;

import com.company.cattos.dto.ClubDto;
import com.company.cattos.model.User;

import java.util.List;
import java.util.UUID;

public interface ClubService {

    void create(ClubDto clubDTO, User user);

    ClubDto findClubByUuid(UUID uuid);

    ClubDto findClubByMemberUuid(UUID memberUuid);

    List<ClubDto> findAll();

    void delete(UUID uuid);

    void update(UUID clubUuid, ClubDto clubDto);


}
