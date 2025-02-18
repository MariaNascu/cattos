package com.company.cattos.service;

import com.company.cattos.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.security.core.Authentication;

import java.util.Optional;
import java.util.UUID;

public interface UserService {

//    Optional<User> saveNewUserToDB(Authentication authentication) throws JsonProcessingException;

    User findUserByUuid(UUID uuid);

    Optional<User> findUserByEmail(String email);

}
