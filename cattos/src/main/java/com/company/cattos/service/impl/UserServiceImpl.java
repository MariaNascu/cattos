package com.company.cattos.service.impl;

import com.auth0.jwt.interfaces.Claim;
import com.company.cattos.enums.Role;
import com.company.cattos.model.User;
import com.company.cattos.security.repository.UserRepository;
import com.company.cattos.security.service.UserDetailsServiceImpl;
import com.company.cattos.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Log4j2
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

//    @Override
//    public Optional<User> saveNewUserToDB(Authentication authentication) throws JsonProcessingException {
//        User user = new User();
//
//        ObjectMapper mapper = new ObjectMapper();
//        ObjectReader reader = mapper.readerFor(Role.class);
//        Map<String, Claim> details = (Map<String, Claim>) ((UserDetails) authentication).;
//        String email = String.valueOf(details.get("email"));
//        Role role = reader.with(DeserializationFeature.READ_ENUMS_USING_TO_STRING).readValue(details.get("role").toString());
//
//        if (userRepository.existsByEmail(email)) {
//
//            log.info("User already exists!");
//            return userRepository.findUserByEmail(email);
//        } else {
//            user.setEmail(details.get("email").toString());
//            user.setFirstName(details.get("firstName").toString());
//            user.setLastName(details.get("lastName").toString());
//            user.setPhoneNumber(details.get("phoneNumber").toString());
//            user.setRoles(List.of(role));
//
//            log.info("Saved new user in the database!");
//            userRepository.save(user);
//        }
//        log.info("User{}", user);
//        return Optional.of(user);
//    }

    @Override
    public User findUserByUuid(UUID uuid) {
        return userRepository.findUserByUuid(uuid);
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }


}
