package org.pollub.campusmate.web;

import lombok.AllArgsConstructor;
import org.pollub.campusmate.entity.Event;
import org.pollub.campusmate.service.EventService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/events")
public class EventController {

    EventService eventService;

    @GetMapping("/{eventId}")
    public ResponseEntity<Event> getEvent(@PathVariable Long eventId) {
        return new ResponseEntity<>(eventService.getEvent(eventId), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Event> createEvent(@RequestBody Event event) {
        return new ResponseEntity<>(eventService.addEvent(event), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{eventId}")
    public ResponseEntity<HttpStatus> deleteEvent(@PathVariable Long eventId) {
        eventService.deleteEvent(eventId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Event>> getAllEvents() {
        return new ResponseEntity<>(eventService.getAllEvents(), HttpStatus.OK);
    }
}
