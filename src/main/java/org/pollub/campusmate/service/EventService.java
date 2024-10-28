package org.pollub.campusmate.service;

import lombok.AllArgsConstructor;
import org.pollub.campusmate.entity.Event;
import org.pollub.campusmate.exception.EventNotFound;
import org.pollub.campusmate.repository.EventRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class EventService {

    //TODO: exception handling

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

    //TODO: update event method

    public List<Event> getAllEvents() {
        List<Event> foundEvents = (List<Event>)eventRepository.findAll();

        if(foundEvents.isEmpty()){
            throw new EventNotFound("No events found");
        }

        return foundEvents;
    }
}