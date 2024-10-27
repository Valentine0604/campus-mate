package org.pollub.campusmate.service;

import lombok.AllArgsConstructor;
import org.pollub.campusmate.entity.Event;
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
        return eventRepository.findById(eventId).orElseThrow(null);
    }

    public Event addEvent(Event event) {
        return eventRepository.save(event);
    }

    public void deleteEvent(long eventId) {
//        if(!eventRepository.existsById(eventId)) {
//
//        }
        eventRepository.deleteById(eventId);
    }

    //TODO: update event method

    public List<Event> getAllEvents() {
        Iterable<Event> events = eventRepository.findAll();
        List<Event> foundEvents = new ArrayList<>();

        events.forEach(foundEvents::add);

        if(foundEvents.isEmpty()){
            return null;
        }

        return foundEvents;
    }
}