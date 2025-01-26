package org.pollub.campusmate.unitTests;

import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.pollub.campusmate.event.dto.EventCreationDto;
import org.pollub.campusmate.event.dto.EventDto;
import org.pollub.campusmate.event.entity.Event;
import org.pollub.campusmate.event.mapper.EventCreationMapper;
import org.pollub.campusmate.event.repository.EventRepository;
import org.pollub.campusmate.event.service.EventService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EventServiceTest {
    @Mock
    private EventRepository eventRepository;

    @Mock
    private EventCreationMapper eventCreationMapper;

    @InjectMocks
    private EventService eventService;

    private Event testEvent;
    private EventDto testEventDto;
    private EventCreationDto testEventCreationDto;

    @BeforeEach
    void setUp() {
        testEvent = new Event();
        testEvent.setEventId(1L);
        testEvent.setEventName("Test Event");
        testEvent.setEventDescription("Test Description");
        testEvent.setStartDate(LocalDate.now());
        testEvent.setEndDate(LocalDate.now().plusDays(1));

        testEventDto = new EventDto(
                1L,
                "Test Event",
                "Test Description",
                LocalDate.now(),
                LocalDate.now().plusDays(1)
        );

        testEventCreationDto = new EventCreationDto(
                "Test Event",
                "Test Description",
                LocalDate.now(),
                LocalDate.now().plusDays(1),
                1L
        );
    }

    @Test
    void getEvent_ShouldReturnEvent() {
        when(eventRepository.findById(1L)).thenReturn(Optional.of(testEvent));

        Event result = eventService.getEvent(1L);

        assertNotNull(result);
        assertEquals("Test Event", result.getEventName());
    }

    @Test
    void addEvent_ShouldCreateNewEvent() {
        when(eventCreationMapper.toEntity(any(EventCreationDto.class)))
                .thenReturn(testEvent);
        when(eventRepository.save(any(Event.class))).thenReturn(testEvent);

        Event result = eventService.addEvent(testEventCreationDto);

        assertNotNull(result);
        verify(eventRepository).save(any(Event.class));
    }

    @Test
    void updateEvent_ShouldUpdateExistingEvent() {
        when(eventRepository.findById(1L)).thenReturn(Optional.of(testEvent));

        eventService.updateEvent(1L, testEventDto);

        verify(eventRepository).save(any(Event.class));
    }

    @Test
    void deleteEvent_ShouldDeleteEvent() {
        when(eventRepository.findById(1L)).thenReturn(Optional.of(testEvent));

        eventService.deleteEvent(1L);

        verify(eventRepository).delete(testEvent);
    }

    @Test
    void getAllEvents_ShouldReturnAllEvents() {
        when(eventRepository.findAll()).thenReturn(Arrays.asList(testEvent));

        List<Event> results = eventService.getAllEvents();

        assertFalse(results.isEmpty());
        assertEquals(1, results.size());
    }
}
