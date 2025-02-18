package com.company.cattos.service;

import com.company.cattos.dto.MemberDto;

import java.util.List;
import java.util.UUID;

public interface MemberService {
    void create(MemberDto memberDto);
    MemberDto findByUuid(UUID uuid);
    MemberDto findByUsername(String username);
    List<MemberDto> findAll();

}
