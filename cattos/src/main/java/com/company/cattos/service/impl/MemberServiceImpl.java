package com.company.cattos.service.impl;

import com.company.cattos.dto.MemberDto;
import com.company.cattos.mapper.MemberMapper;
import com.company.cattos.model.Member;
import com.company.cattos.repository.MemberRepository;
import com.company.cattos.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {

    public final MemberRepository memberRepository;
    public final MemberMapper memberMapper;

    @Override
    public void create(MemberDto memberDto) {
        Member member = new Member();
        memberMapper.mapToMember(memberDto, member);
        Member savedMember = memberRepository.save(member);
        memberMapper.mapToMemberDto(savedMember);
    }

    @Override
    public MemberDto findByUuid(UUID uuid) {
        Member member = memberRepository.findByUuid(uuid);
        return memberMapper.mapToMemberDto(member);
    }

    @Override
    public MemberDto findByUsername(String username) {
        Member member = memberRepository.findByUsername(username);
        return memberMapper.mapToMemberDto(member);
    }

    @Override
    public List<MemberDto> findAll() {
        List<Member> memberList = memberRepository.findAll();
        List<MemberDto> memberDtoList = new ArrayList<>();

        for (Member member : memberList) {
            MemberDto memberDto = memberMapper.mapToMemberDto(member);
            memberDtoList.add(memberDto);
        }
        return memberDtoList;
    }

}
