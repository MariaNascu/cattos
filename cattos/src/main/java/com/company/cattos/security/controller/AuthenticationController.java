package com.company.cattos.security.controller;

import com.company.cattos.security.dto.AuthResponseDto;
import com.company.cattos.security.dto.UserDto;
import com.company.cattos.security.jwt.service.JWTService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth/token")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;


    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserDto registerDto) {
        jwtService.registerUserIfNotRegistered(registerDto);
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        registerDto.getEmail(),
                        registerDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return ResponseEntity.ok("User has been registered! Please login to get the access token!");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody UserDto loginDto) {
        boolean isUserRegistered = jwtService.isUserRegistered(loginDto);

        if (isUserRegistered) {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDto.getEmail(),
                            loginDto.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtService.generateToken(authentication);
            return ResponseEntity.ok(new AuthResponseDto(token));
        }
        return ResponseEntity.notFound().build();
    }

}
