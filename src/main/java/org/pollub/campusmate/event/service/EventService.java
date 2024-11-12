package org.pollub.campusmate.event.service;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.pollub.campusmate.event.entity.Event;
import org.pollub.campusmate.event.exception.EventNotFound;
import org.pollub.campusmate.event.repository.EventRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EventService {

    private final EventRepository eventRepository;

    public Event getEvent(long eventId) {

        return eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFound("Event with id " + eventId + " not found"));
    }

    public Event addEvent(Event event) {
        return eventRepository.save(event);
    }

    public void deleteEvent(long eventId) {
        if(!eventRepository.existsById(eventId)) {
            throw new EventNotFound("Cannot execute delete operation. Event with id " + eventId + " not found");
        }
        eventRepository.deleteById(eventId);
    }

    public void updateEvent(Long eventId, @Valid Event event) {
        if(!eventRepository.existsById(eventId)) {
            throw new EventNotFound("Cannot execute update operation. Event with id " + eventId + " not found");
        }
        Event foundEvent = eventRepository.findById(eventId).get();
        event.setEventId(foundEvent.getEventId());
        event.setTeam(foundEvent.getTeam());
        event.setCalendar(foundEvent.getCalendar());
        eventRepository.save(event);
    }

    public List<Event> getAllEvents() {
        List<Event> foundEvents = (List<Event>)eventRepository.findAll();

        if(foundEvents.isEmpty()){
            throw new EventNotFound("No events found");
        }

        return foundEvents;
    }


}