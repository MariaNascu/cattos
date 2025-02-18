package com.company.cattos.mapper;

import com.company.cattos.dto.EventDto;
import com.company.cattos.exception.CattosException;
import com.company.cattos.model.Club;
import com.company.cattos.model.Event;
import com.company.cattos.repository.ClubRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mapstruct.*;

import java.util.UUID;

@NoArgsConstructor(force = true)
@AllArgsConstructor
@Getter
@Setter
@Mapper(componentModel = "spring",
        uses = {MemberMapper.class,
                ClubMapper.class})
public abstract class EventMapper {
    private final ClubRepository clubRepository;

    @Mapping(source = "club.uuid", target = "clubUuid")
    public abstract EventDto mapToEventDto(Event event);

    @Mapping(source = "clubUuid", target = "club", qualifiedByName = "uuidToClub")
    @InheritInverseConfiguration
    public abstract void mapToEvent(EventDto eventDto, @MappingTarget Event event);

    @Named("uuidToClub")
    public Club uuidToClub(UUID uuid) {
        if (clubRepository != null) {
            return clubRepository.findClubByUuid(uuid);
        } else {
            throw CattosException.of("The club doesn't exist!");
        }
    }
}

