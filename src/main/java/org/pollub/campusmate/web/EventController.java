package org.pollub.campusmate.web;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.pollub.campusmate.entity.Event;
import org.pollub.campusmate.service.EventService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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

    @PostMapping
    public ResponseEntity<String> createEvent(@Valid @RequestBody Event event, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            bindingResult.getAllErrors().forEach(error -> errorMessage.append(error.getDefaultMessage()).append("\n"));
            return new ResponseEntity<>(errorMessage.toString(), HttpStatus.BAD_REQUEST);
        }
        eventService.addEvent(event);
        return new ResponseEntity<>("Event created successfully", HttpStatus.CREATED);
    }


    @DeleteMapping("/{eventId}")
    public ResponseEntity<String> deleteEvent(@PathVariable Long eventId) {
        eventService.deleteEvent(eventId);
        return new ResponseEntity<>("Event deleted successfully", HttpStatus.NO_CONTENT);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Event>> getAllEvents() {
        return new ResponseEntity<>(eventService.getAllEvents(), HttpStatus.OK);
    }
}
