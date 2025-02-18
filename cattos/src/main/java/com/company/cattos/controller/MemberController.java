package com.company.cattos.controller;

import com.company.cattos.dto.ClubDto;
import com.company.cattos.dto.MemberDto;
import com.company.cattos.enums.Role;
import com.company.cattos.service.ClubMemberService;
import com.company.cattos.service.MemberService;
import com.company.cattos.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {

    private final MemberService memberService;
    private final ClubMemberService clubMemberService;
    private final UserService userService;

    // create club
    @PostMapping("/new")
    public ResponseEntity<MemberDto> create(@RequestBody MemberDto memberDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        var email = authentication.getName();
        var user = userService.findUserByEmail(email);
        memberDto.setUserId(user.get().getId());
        memberService.create(memberDto);

        return ResponseEntity.ok(memberDto);
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<MemberDto>> findAllMembers() {
        List<MemberDto> list = memberService.findAll();

        return ResponseEntity.ok(list);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> delete(@RequestParam UUID memberUuid, @RequestParam UUID clubUuid) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        ClubDto clubDto = memberService.findByUuid(memberUuid).getClubDto();

        var user = (UserDetails) authentication.getPrincipal();
        var userUsername = user.getUsername();
        var isAdmin = clubDto.getMembers().stream()
                .anyMatch(p -> p.getUsername().equals(userUsername) && p.getRole() == Role.ADMIN);

        if (isAdmin) {
            clubMemberService.delete(memberUuid, clubUuid);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

    }
}
