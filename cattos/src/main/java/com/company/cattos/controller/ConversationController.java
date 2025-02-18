package com.company.cattos.controller;

import com.company.cattos.dto.ConversationDto;
import com.company.cattos.dto.MemberDto;
import com.company.cattos.service.*;
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
@RequestMapping("/api/conversation")
public class ConversationController {

    private final ConversationService conversationService;
    private final ConversationMessageService conversationMessageService;
    private final ClubService clubService;
    private final MemberService memberService;


    @PostMapping("/new")
    public ResponseEntity<ConversationDto> create(@RequestBody ConversationDto conversationDto,
                                                  @RequestParam UUID clubUuid,
                                                  @RequestParam UUID conversationMemberUuid){
            MemberDto memberDto = getMemberDto();

            if (memberDto != null){
                var clubDto = clubService.findClubByMemberUuid(memberDto.getUuid());
                if (clubDto.getUuid().toString().equals(clubUuid.toString())){
                    conversationService.create(clubUuid,conversationMemberUuid, conversationDto);
                }
            }
            return ResponseEntity.ok(conversationDto);
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<ConversationDto>> findAll() {
        MemberDto memberDto = getMemberDto();

        if (memberDto != null) {
            var clubDto = clubService.findClubByMemberUuid(memberDto.getUuid());
            List<ConversationDto> list = conversationService.findAllFromClubByMemberUuid(memberDto.getUuid(), clubDto.getUuid());

            return ResponseEntity.ok(list);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> delete(@RequestParam UUID conversationUuid) {
        MemberDto memberDto = getMemberDto();

        if (memberDto != null) {
            var clubUuid = clubService.findClubByMemberUuid(memberDto.getUuid()).getUuid();
            if (clubUuid != null) {
                conversationMessageService.delete(conversationUuid,clubUuid);
            }
        }
        return ResponseEntity.noContent().build();
    }

    private MemberDto getMemberDto() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        var user = (UserDetails) authentication.getPrincipal();
        var userUsername = user.getUsername();

        return memberService.findByUsername(userUsername);
    }
}
