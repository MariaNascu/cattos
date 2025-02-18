package com.company.cattos.controller;

import com.company.cattos.dto.ClubDto;
import com.company.cattos.exception.CattosException;
import com.company.cattos.model.User;
import com.company.cattos.service.ClubService;
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
import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/club")
public class ClubController {

    private final UserService userService;
    private final ClubService clubService;


    // create new club
    @PostMapping("/new")
    public ResponseEntity<Void> create(@RequestBody ClubDto clubDTO){
        Optional<User> user  = getUserFromSecurityContext();
        clubService.create(clubDTO, user.get());

        return ResponseEntity.noContent().build();
    }

    // list all existing clubs
    @GetMapping("/findAll")
    public ResponseEntity<List<ClubDto>> findAll() {
        List<ClubDto> list = clubService.findAll();

        return ResponseEntity.ok(list);
    }

    // create new club
    @PutMapping("/update")
    public ResponseEntity<ClubDto> update(@RequestParam UUID clubUuid, @RequestBody ClubDto clubDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername();
        Optional<User> user = userService.findUserByEmail(email);
        ClubDto club = clubService.findClubByUuid(clubUuid);
        boolean isMember = club.getMembers()
                .stream().anyMatch(m -> m.getUuid().toString().equals(user.get().getUuid().toString()));
        if (isMember) {
            clubService.update(clubUuid, clubDTO);
        }


        return ResponseEntity.ok(clubDTO);
    }


    //delete club by uuid (single delete)
    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteClub(@RequestParam UUID clubUuid) {
        try {
            clubService.delete(clubUuid);
        } catch (CattosException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.noContent().build();
    }
    private Optional<User> getUserFromSecurityContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return Optional.empty();
        }
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails userDetails) {
            String email = userDetails.getUsername();
            return userService.findUserByEmail(email);
        }
        return Optional.empty();
    }
}
