package com.company.cattos.service.impl;

import com.company.cattos.dto.ClubDto;
import com.company.cattos.enums.Role;
import com.company.cattos.exception.CattosException;
import com.company.cattos.mapper.ClubMapper;
import com.company.cattos.model.Club;
import com.company.cattos.model.Member;
import com.company.cattos.model.User;
import com.company.cattos.repository.ClubRepository;
import com.company.cattos.repository.MemberRepository;
import com.company.cattos.security.repository.UserRepository;
import com.company.cattos.service.ClubService;
import com.company.cattos.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ClubServiceImpl implements ClubService {

    private final ClubRepository clubRepository;
    private final MemberService memberService;
    private final ClubMapper clubMapper;
    private final MemberRepository memberRepository;
    private final UserRepository userRepository;

    @Override
    public void create(ClubDto clubDto, User user) {
        var club = new Club();
        clubMapper.mapToClub(clubDto, club);
        var savedClub = clubRepository.save(club);

        var member = new Member();
        member.setClub(savedClub);
        member.setUser(user);
        member.setRole(Role.ADMIN);
        member.setUsername(user.getEmail());
        var savedMember = memberRepository.save(member);

        var addedClub = savedClub.addMember(savedMember);
        clubRepository.save(addedClub);

        var addedUser = user.addMember(savedMember);
        userRepository.save(addedUser);

        clubMapper.mapToClubDto(savedClub);
    }

    @Override
    public ClubDto findClubByUuid(UUID uuid) {
        var club = clubRepository.findClubByUuid(uuid);
        return clubMapper.mapToClubDto(club);
    }

    public List<ClubDto> findAll() {
        List<Club> clubList = clubRepository.findAll();
        List<ClubDto> clubDtoList = new ArrayList<>();

        for (Club club : clubList) {
            var clubDto = clubMapper.mapToClubDto(club);
            clubDtoList.add(clubDto);
        }
        return clubDtoList;
    }


    @Override
    public void delete(UUID uuid) {
        var club = clubRepository.findClubByUuid(uuid);

        if (club != null) {
            clubRepository.delete(club);
        } else {
            throw CattosException.of("The club with UUID %s doesn't exist!", uuid);
        }
    }

    @Override
    public void update(UUID clubUuid, ClubDto clubDto) {
        var club = clubRepository.findClubByUuid(clubUuid);
        clubMapper.mapToClub(clubDto, club);

        clubRepository.save(club);
    }

    @Override
    public ClubDto findClubByMemberUuid(UUID memberUuid) {

        var memberDto = memberService.findByUuid(memberUuid);
        var clubUUid = memberDto.getClubDto().getUuid();
        var club = clubRepository.findClubByUuid(clubUUid);

        return clubMapper.mapToClubDto(club);
    }
}
