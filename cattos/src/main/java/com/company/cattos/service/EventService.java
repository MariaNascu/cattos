package com.company.cattos.service;

import com.company.cattos.dto.EventDto;

import java.util.List;
import java.util.UUID;

public interface EventService {

    void create(EventDto eventDto, UUID memberUuid);

    List<EventDto> findAllEvents();

    void delete(UUID memberUuid, UUID clubUuid, UUID eventUuid);
}
