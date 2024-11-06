package org.pollub.campusmate.web;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.pollub.campusmate.dto.CalendarCreationDTO;
import org.pollub.campusmate.dto.CalendarDTO;
import org.pollub.campusmate.entity.Calendar;
import org.pollub.campusmate.service.CalendarService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/calendars")
public class CalendarController {

    private final ModelMapper modelMapper;
    CalendarService calendarService;

    @GetMapping("/{calendarId}")
    public ResponseEntity<CalendarDTO> getCalendar(@PathVariable Long calendarId) {
        Calendar calendar = calendarService.getCalendar(calendarId);
        CalendarDTO calendarDTO = modelMapper.map(calendar, CalendarDTO.class);
        return new ResponseEntity<>(calendarDTO, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CalendarDTO>> getCalendarsByUser(@PathVariable Long userId) {
        List<Calendar> calendars = calendarService.getCalendarsByUser(userId);
        List<CalendarDTO> calendarsDTO = calendars.stream().map(calendar -> modelMapper.map(calendar, CalendarDTO.class)).toList();
        return new ResponseEntity<>(calendarsDTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> createCalendar(@RequestBody CalendarCreationDTO calendarDTO) {
        Calendar calendar = modelMapper.map(calendarDTO, Calendar.class);
        calendarService.createCalendar(calendar);
        return new ResponseEntity<>("Calendar created successfully",HttpStatus.CREATED);
    }

    @DeleteMapping("/{calendarId}")
    public ResponseEntity<String> deleteCalendar(@PathVariable Long calendarId) {
        calendarService.deleteCalendar(calendarId);
        return new ResponseEntity<>("Calendar deleted successfully",HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{calendarId}")
    public ResponseEntity<String> updateCalendar(@PathVariable Long calendarId, @RequestBody CalendarCreationDTO calendarDTO) {
        Calendar calendar = modelMapper.map(calendarDTO, Calendar.class);
        calendarService.updateCalendar(calendarId, calendar);
        return new ResponseEntity<>("Calendar updated successfully",HttpStatus.OK);
    }



    @GetMapping
    public ResponseEntity<List<CalendarDTO>> getAllCalendars() {
        List<CalendarDTO> calendarsDTO = calendarService.getAllCalendars().stream().map(calendar -> modelMapper.map(calendar, CalendarDTO.class)).toList();
        return new ResponseEntity<>(calendarsDTO, HttpStatus.OK);
    }

}
