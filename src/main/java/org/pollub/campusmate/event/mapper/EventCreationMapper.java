package org.pollub.campusmate.event.mapper;

import lombok.AllArgsConstructor;
import org.pollub.campusmate.event.dto.EventCreationDto;
import org.pollub.campusmate.event.entity.Event;
import org.pollub.campusmate.team.service.TeamService;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class EventCreationMapper {

    private final TeamService teamService;



    public EventCreationDto toDto(Event event) {
        if (event == null) return null;

        return new EventCreationDto(
                event.getEventName(),
                event.getEventDescription(),
                event.getStartDate(),
                event.getEndDate(),
                event.getTeam().getTeamId()
        );
    }

    public Event toEntity(EventCreationDto eventCreationDto) {
        if (eventCreationDto == null) return null;

        Event event = new Event();
        event.setEventName(eventCreationDto.getEventName());
        event.setEventDescription(eventCreationDto.getEventDescription());
        event.setStartDate(eventCreationDto.getStartDate());
        event.setEndDate(eventCreationDto.getEndDate());
        event.setTeam(teamService.getTeam(eventCreationDto.getTeamId()));

        return event;
    }
}
