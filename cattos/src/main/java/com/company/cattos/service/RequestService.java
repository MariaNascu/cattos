package com.company.cattos.service;


import com.company.cattos.dto.RequestDto;
import com.company.cattos.enums.RequestStatus;
import com.company.cattos.enums.Role;
import com.company.cattos.exception.CattosException;
import com.company.cattos.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RequestService {

    void createRequest(UUID clubUuid, UUID userUuid);

    RequestDto findRequestByUuid(UUID uuid);

    void closeRequest(UUID uuid, RequestStatus status, UUID userUuid);

    void removeRequest(UUID uuid) throws CattosException;

    List<RequestDto> findAllRequests();
}
