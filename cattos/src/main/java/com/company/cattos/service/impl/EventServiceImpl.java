package com.company.cattos.service.impl;

import com.company.cattos.dto.EventDto;
import com.company.cattos.exception.CattosException;
import com.company.cattos.mapper.EventMapper;
import com.company.cattos.model.Event;
import com.company.cattos.model.Member;
import com.company.cattos.repository.EventRepository;
import com.company.cattos.service.ClubMemberService;
import com.company.cattos.service.EventService;
import com.company.cattos.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final MemberService memberService;
    private final ClubMemberService clubMemberService;
    private final EventMapper eventMapper;

    @Override
    public void create(EventDto eventDto, UUID memberUuId) {
        Event event = new Event();
        eventMapper.mapToEvent(eventDto, event);
        event.setCreatorId(memberUuId);
        eventRepository.save(event);
    }

    @Override
    public List<EventDto> findAllEvents() {
        List<Event> events = eventRepository.findAll();
        List<EventDto> eventDtos = new ArrayList<>();

        for (Event event : events) {
            EventDto eventDto = eventMapper.mapToEventDto(event);
            eventDtos.add(eventDto);
        }
        return eventDtos;

    }

    @Override
    public void delete(UUID memberUuid, UUID clubuuid, UUID eventUuid) {
        var clubMemberUuid = memberService.findByUuid(memberUuid).getUuid();
        var event = eventRepository.findByUuid(eventUuid);

        if (!(clubuuid.toString().equals(clubMemberUuid.toString()))) {
            throw CattosException.of("The member is not pertaining to the specified club!");
        }
//        for (Member participant: event.getParticipants()) {
//            event.removeParticipant(participant);
//        }
        eventRepository.deleteByUuid(eventUuid);
    }
}
