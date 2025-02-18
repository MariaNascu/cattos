package com.company.cattos.controller;


import com.company.cattos.dto.EventDto;
import com.company.cattos.exception.CattosException;
import com.company.cattos.service.EventService;
import com.company.cattos.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/event")
public class EventController {

    private final EventService eventService;
    private final UserService userService;

    @PostMapping("/new")
    public ResponseEntity<EventDto> createEvent(@RequestParam UUID memberUuid, @RequestBody EventDto eventDto) {
        eventService.create(eventDto, memberUuid);
        return ResponseEntity.ok(eventDto);
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<EventDto>> findAllEvents() {
        List<EventDto> events = eventService.findAllEvents();
        return ResponseEntity.ok(events);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> delete(@RequestParam UUID memberUuid,
                                       @RequestParam UUID eventUuid,
                                       @RequestParam UUID clubUuid) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        var user = (UserDetails) authentication.getPrincipal();
        var userUsername = user.getUsername();

        var userDto = userService.findUserByEmail(userUsername);
        var userUuid = userDto.get().getUuid();

        if (!userUuid.equals(memberUuid)) {
            throw CattosException.of("The user is not a member!");
        }

        eventService.delete(memberUuid,clubUuid, eventUuid);

        return ResponseEntity.noContent().build();
    }
}
