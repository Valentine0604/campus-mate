package org.pollub.campusmate.event.mapper;

import org.pollub.campusmate.event.dto.EventCreationDto;
import org.pollub.campusmate.event.entity.Event;
import org.springframework.stereotype.Component;

@Component
public class EventCreationMapper {

    public EventCreationDto toDto(Event event) {
        if (event == null) return null;

        return new EventCreationDto(
                event.getEventName(),
                event.getEventDescription(),
                event.getStartDate(),
                event.getEndDate(),
                event.getTeam()
        );
    }

    public Event toEntity(EventCreationDto eventCreationDto) {
        if (eventCreationDto == null) return null;

        Event event = new Event();
        event.setEventName(eventCreationDto.getEventName());
        event.setEventDescription(eventCreationDto.getEventDescription());
        event.setStartDate(eventCreationDto.getStartDate());
        event.setEndDate(eventCreationDto.getEndDate());
        event.setTeam(eventCreationDto.getTeam());

        return event;
    }
}
