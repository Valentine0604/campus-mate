package org.pollub.campusmate.event.web;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.pollub.campusmate.event.dto.EventCreationDto;
import org.pollub.campusmate.event.dto.EventDto;
import org.pollub.campusmate.event.mapper.EventCreationMapper;
import org.pollub.campusmate.event.mapper.EventMapper;
import org.pollub.campusmate.event.service.EventService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/api/event")
public class EventController {

    private final EventMapper eventMapper;
    private final EventCreationMapper eventCreationMapper;
    private final EventService eventService;

    @GetMapping("/{eventId}")
    public ResponseEntity<EventDto> getEvent(@PathVariable Long eventId) {
        var event = eventService.getEvent(eventId);
        var eventDto = eventMapper.toDto(event);
        return new ResponseEntity<>(eventDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> createEvent(
            @Valid @RequestBody EventCreationDto eventCreationDto,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getAllErrors().stream()
                    .map(error -> error.getDefaultMessage() + "\n")
                    .collect(Collectors.joining());
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }

        eventService.addEvent(eventCreationDto);
        return new ResponseEntity<>("Event created successfully", HttpStatus.CREATED);
    }

    @PatchMapping("/{eventId}")
    public ResponseEntity<String> updateEvent(
            @PathVariable Long eventId,
            @Valid @RequestBody EventDto eventDto,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getAllErrors().stream()
                    .map(error -> error.getDefaultMessage() + "\n")
                    .collect(Collectors.joining());
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }

        eventService.updateEvent(eventId, eventDto);
        return new ResponseEntity<>("Event updated successfully", HttpStatus.OK);
    }

    @DeleteMapping("/{eventId}")
    public ResponseEntity<String> deleteEvent(@PathVariable Long eventId) {
        eventService.deleteEvent(eventId);
        return new ResponseEntity<>("Event deleted successfully", HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<List<EventDto>> getAllEvents() {
        var tempEvents = eventService.getAllEvents();
        var events = eventService.getAllEvents().stream()
                .map(eventMapper::toDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(events, HttpStatus.OK);
    }


}