package com.company.cattos.service.impl;

import com.company.cattos.exception.CattosException;
import com.company.cattos.model.Club;
import com.company.cattos.model.Member;
import com.company.cattos.repository.ClubRepository;
import com.company.cattos.repository.MemberRepository;
import com.company.cattos.service.ClubMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ClubMemberServiceImpl implements ClubMemberService {
    private final ClubRepository clubRepository;
    private final MemberRepository memberRepository;

    @Override
    public void delete(UUID memberUuid, UUID clubUuid) {
        Member member = memberRepository.findByUuid(memberUuid);
        if (member == null) {
            throw CattosException.of("Member not found!");
        }

        Club club = clubRepository.findClubByUuid(clubUuid);

        if (club == null) {
            throw CattosException.of("Club not found!");
        }
        UUID clubMemberUuid = member.getClub().getUuid();
        if (!(clubMemberUuid.toString().equals(clubUuid.toString()))) {
            throw CattosException.of("The user isn't a member to the specified club!");
        }

//        club.removeMember(member);
        clubRepository.save(club);
        memberRepository.delete(member);


    }
}
