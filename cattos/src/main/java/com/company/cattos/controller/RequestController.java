package com.company.cattos.controller;

import com.company.cattos.dto.RequestDto;
import com.company.cattos.dto.RequestStatusDto;
import com.company.cattos.enums.RequestStatus;
import com.company.cattos.exception.CattosException;
import com.company.cattos.model.User;
import com.company.cattos.security.SecurityUser;
import com.company.cattos.service.RequestService;
import com.company.cattos.service.UserService;
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
@RequestMapping("/api/request")
public class RequestController {

    private final UserService userService;
    private final RequestService requestService;

    // submit a request to join a club
    @PostMapping("/join")
    public ResponseEntity<Void> joinRequest(@RequestParam UUID clubUuid) {
        Optional<User> user = getUserFromSecurityContext();
        requestService.createRequest(clubUuid, user.get().getUuid());

        return ResponseEntity.ok(null);
    }

    // get a list of all the requests (used by the Club administrator to approve or decline requests)
    @GetMapping("/findAll")
    public ResponseEntity<List<RequestDto>> findAllRequests() {
        List<RequestDto> list = requestService.findAllRequests();

        return ResponseEntity.ok(list);
    }

    // close requests (used by the Club administrator)
    @PostMapping("/close")
    public ResponseEntity<Void> closeRequest(@RequestParam UUID uuid,
                                                   @RequestBody RequestStatusDto status) {
        Optional<User> user = getUserFromSecurityContext();
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        RequestStatus requestStatus = status.getStatus();
        requestService.closeRequest(uuid, requestStatus, user.get().getUuid());
//        requestService.removeRequest(uuid);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteRequest(UUID requestUuid) {
        try {
            requestService.removeRequest(requestUuid);
        } catch (CattosException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
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
