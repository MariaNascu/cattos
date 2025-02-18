package com.company.cattos.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth/token")
public class AuthenticationController {
    private static final String SECRET_KEY = "e916fb27120313cd80fc6de375f51c7bbe82f234a121d510d29453abe8ddbfe478d615e465751c59eb631cdad7e15c5bcec464b809d07178933496662555519f";

    @PostMapping("/create")
    public String createToken(@RequestBody AuthProviderDto authProviderDto) {
        return JWT.create()
                .withClaim("email", authProviderDto.getEmail())
                .withClaim("firstName", authProviderDto.getFirstName())
                .withClaim("lastName", authProviderDto.getLastName())
                .withClaim("phoneNumber", authProviderDto.getPhone()    )
                .withClaim("role", authProviderDto.getRole())
                .withIssuedAt(Date.from(Instant.now()))
                .withExpiresAt(Date.from(Instant.now().plus(30, ChronoUnit.DAYS)))
                .sign(Algorithm.HMAC256(SECRET_KEY.getBytes()));

    }

}
