package org.pollub.campusmate.web;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.pollub.campusmate.dto.EventDTO;
import org.pollub.campusmate.entity.Event;
import org.pollub.campusmate.service.EventService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/event")
public class EventController {

    private final ModelMapper modelMapper;
    EventService eventService;

    @GetMapping("/{eventId}")
    public ResponseEntity<EventDTO> getEvent(@PathVariable Long eventId) {
        Event event = eventService.getEvent(eventId);
        EventDTO eventDTO = modelMapper.map(event, EventDTO.class);
        return new ResponseEntity<>(eventDTO, HttpStatus.OK);
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

    @PutMapping
    public ResponseEntity<String> updateEvent(@PathVariable Long eventId, @Valid @RequestBody EventDTO eventDTO, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            bindingResult.getAllErrors().forEach(error -> errorMessage.append(error.getDefaultMessage()).append("\n"));
            return new ResponseEntity<>(errorMessage.toString(), HttpStatus.BAD_REQUEST);
        }

        Event event = modelMapper.map(eventDTO, Event.class);
        eventService.updateEvent(eventId, event);
        return new ResponseEntity<>("Event updated successfully", HttpStatus.OK);
    }


    @DeleteMapping("/{eventId}")
    public ResponseEntity<String> deleteEvent(@PathVariable Long eventId) {
        eventService.deleteEvent(eventId);
        return new ResponseEntity<>("Event deleted successfully", HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<List<EventDTO>> getAllEvents() {
        return new ResponseEntity<>(eventService.getAllEvents().stream().map(event -> modelMapper.map(event, EventDTO.class)).toList(), HttpStatus.OK);
    }
}
