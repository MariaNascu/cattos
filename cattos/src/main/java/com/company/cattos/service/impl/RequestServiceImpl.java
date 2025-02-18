package com.company.cattos.service.impl;

import com.company.cattos.dto.MemberDto;
import com.company.cattos.dto.RequestDto;
import com.company.cattos.enums.RequestStatus;
import com.company.cattos.enums.Role;
import com.company.cattos.exception.CattosException;
import com.company.cattos.mapper.MemberMapper;
import com.company.cattos.mapper.RequestMapper;
import com.company.cattos.model.Club;
import com.company.cattos.model.Member;
import com.company.cattos.model.Request;
import com.company.cattos.model.User;
import com.company.cattos.repository.ClubRepository;
import com.company.cattos.repository.MemberRepository;
import com.company.cattos.repository.RequestRepository;
import com.company.cattos.security.repository.UserRepository;
import com.company.cattos.service.RequestService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@RequiredArgsConstructor
@Service
public class RequestServiceImpl implements RequestService {

    private final ClubRepository clubRepository;
    private final RequestRepository requestRepository;
    private final UserRepository userRepository;
    private final MemberRepository memberRepository;
    private final RequestMapper requestMapper;

    @Override
    @Transactional
    public void createRequest(UUID clubUuid, UUID userUuid) {
        Request request = new Request();
        request.setUser(userRepository.findUserByUuid(userUuid));
        request.setClub(clubRepository.findClubByUuid(clubUuid));
        request.setStatus(RequestStatus.IN_PROGRESS);
        requestRepository.save(request);
    }

    @Override
    public RequestDto findRequestByUuid(UUID uuid) {
        Request request = requestRepository.findRequestByUuid(uuid);
        return requestMapper.mapToRequestDto(request);
    }

    @Override
    public void closeRequest(UUID requestUuid, RequestStatus status, UUID userUuid) {
        var adminUser = userRepository.findUserByUuid(userUuid);
        boolean isUserAdmin = adminUser.getRoles().stream().map(Role::getName).anyMatch(r -> r.equals(Role.ADMIN.getName()));

        if (!isUserAdmin) {
            throw CattosException.of("The user can only become a member if the club admin approves the request!");
        }
        RequestDto requestDto = findRequestByUuid(requestUuid);
        Request request = new Request();
        requestDto.setStatus(status);
        requestMapper.mapToRequest(requestDto, request);
        requestRepository.save(request);

        if (status == RequestStatus.APPROVED) {
            var club = request.getClub();
            var user = request.getUser();
            var member = new Member();
            member.setRole(Role.MEMBER);
            member.setUsername(user.getEmail());
            var savedMember = memberRepository.save(member);

            var addedClub = club.addMember(savedMember);
            clubRepository.save(addedClub);
            var addedRequest = club.addRequest(request);
            clubRepository.save(addedRequest);
            var addedUser = user.addMember(savedMember);
            userRepository.save(addedUser);
            var addedRequestToUser = user.addRequest(request);
            userRepository.save(addedRequestToUser);
        }
        requestMapper.mapToRequestDto(request);

    }

    @Override
    public void removeRequest(UUID requestUuid) throws CattosException {
        var request = requestRepository.findRequestByUuid(requestUuid);
        if (request.getStatus() == RequestStatus.APPROVED || request
                .getStatus() == RequestStatus.DECLINED) {
            requestRepository.delete(request);
        } else {
            throw CattosException.of("The request has not been reviewed yet!");
        }
    }

    @Override
    public List<RequestDto> findAllRequests() {
        List<Request> requestList = requestRepository.findAll();
        List<RequestDto> requestDtoList = new ArrayList<>();

        for (Request request : requestList) {
            RequestDto requestDto = requestMapper.mapToRequestDto(request);
            requestDtoList.add(requestDto);
        }
        return requestDtoList;
    }

}
