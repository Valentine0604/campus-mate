package org.pollub.campusmate.event.mapper;

import org.pollub.campusmate.event.dto.EventDto;
import org.pollub.campusmate.event.entity.Event;
import org.springframework.stereotype.Component;

@Component
public class EventMapper {

    public EventDto toDto(Event event) {
        if (event == null) return null;


        return new EventDto(
                event.getEventName(),
                event.getEventDescription(),
                event.getStartDate(),
                event.getEndDate()
        );
    }

    public Event toEntity(EventDto eventDto) {
        if (eventDto == null) {
            return null;
        }

        Event event = new Event();
        event.setEventName(eventDto.getEventName());
        event.setEventDescription(eventDto.getEventDescription());
        event.setStartDate(eventDto.getStartDate());
        event.setEndDate(eventDto.getEndDate());

        return event;
    }
}
