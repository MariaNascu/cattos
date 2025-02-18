package com.company.cattos.security.jwt.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.company.cattos.enums.Role;
import com.company.cattos.model.User;
import com.company.cattos.security.dto.UserDto;
import com.company.cattos.security.repository.UserRepository;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
@Data
@Log4j2
public class JWTService {
    @Value("${app.secretKey}")
    private String secretKey;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public String generateToken(Authentication authentication) {
        var currentDate = Date.from(Instant.now());
        var expiryDate = Date.from(Instant.now().plus(2, ChronoUnit.DAYS));
        var details = (UserDetails) authentication.getPrincipal();

        return JWT.create()
                .withClaim("email", details.getUsername())
                .withClaim("password", details.getPassword())
                .withIssuedAt(currentDate)
                .withExpiresAt(expiryDate)
                .sign(Algorithm.HMAC256(secretKey.getBytes(StandardCharsets.UTF_8)));
    }

    public void registerUserIfNotRegistered(UserDto registerDto) {
        Optional<User> optionalUser = userRepository.findUserByEmail(registerDto.getEmail());

        if (optionalUser.isEmpty()) {
            User user = new User();
            user.setEmail(registerDto.getEmail());
            user.setFirstName(registerDto.getFirstName());
            user.setLastName(registerDto.getLastName());
            user.setRoles(List.of(Role.convertStringToRole(registerDto.getRole())));
            user.setPhoneNumber(registerDto.getPhone());
            user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
            userRepository.save(user);
        }
    }

    public boolean isUserRegistered(UserDto userDto) {
        Optional<User> user = userRepository.findUserByEmail(userDto.getEmail());
        return user.isPresent();

    }

    public boolean verifyToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey.getBytes(StandardCharsets.UTF_8));
        JWTVerifier jwtVerifier = JWT.require(algorithm).build();
        try {
            DecodedJWT decodedToken = jwtVerifier.verify(token);
            return !isTokenExpired(decodedToken);
        } catch (JWTVerificationException e) {
            log.info("Invalid JWT token");
            return false;
        }
    }

    public Map<String, Claim> extractAllClaims(Optional<String> token) {
        return token.map(JWT::decode).map(DecodedJWT::getClaims).orElse(Collections.emptyMap());
    }

    private boolean isTokenExpired(DecodedJWT decodedToken) {
        return decodedToken.getExpiresAt().before(new Date());
    }


}


