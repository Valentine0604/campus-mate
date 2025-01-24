package org.pollub.campusmate.event.service;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.pollub.campusmate.event.dto.EventCreationDto;
import org.pollub.campusmate.event.dto.EventDto;
import org.pollub.campusmate.event.entity.Event;
import org.pollub.campusmate.event.exception.EventNotFound;
import org.pollub.campusmate.event.mapper.EventCreationMapper;
import org.pollub.campusmate.event.repository.EventRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final EventCreationMapper eventCreationMapper;

    public Event getEvent(long eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFound("Event with id " + eventId + " not found"));
    }

    public Event addEvent(EventCreationDto event) {
        Event newEvent = eventCreationMapper.toEntity(event);
        return eventRepository.save(newEvent);
    }

    @Transactional
    public void deleteEvent(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFound("Cannot execute delete operation. Event with id " + eventId + " not found"));
        eventRepository.save(event);
        eventRepository.delete(event);
    }

    @Transactional
    public void updateEvent(Long eventId, @Valid EventDto event) {
        Event foundEvent = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFound("Cannot execute update operation. Event with id " + eventId + " not found"));

        if (event.getEventName() != null) {
            foundEvent.setEventName(event.getEventName());
        }
        if (event.getEventDescription() != null) {
            foundEvent.setEventDescription(event.getEventDescription());
        }
        if (event.getStartDate() != null) {
            foundEvent.setStartDate(event.getStartDate());
        }
        if (event.getEndDate() != null) {
            foundEvent.setEndDate(event.getEndDate());
        }
        eventRepository.save(foundEvent);
    }

    public List<Event> getAllEvents() {
        List<Event> foundEvents = (List<Event>) eventRepository.findAll();
        if (foundEvents.isEmpty()) {
            throw new EventNotFound("No events found");
        }
        return foundEvents;
    }
}