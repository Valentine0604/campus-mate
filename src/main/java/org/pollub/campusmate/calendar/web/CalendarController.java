package org.pollub.campusmate.calendar.web;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.pollub.campusmate.calendar.dto.CalendarCreationDto;
import org.pollub.campusmate.calendar.dto.CalendarDto;
import org.pollub.campusmate.calendar.service.CalendarService;
import org.pollub.campusmate.calendar.entity.Calendar;
import org.pollub.campusmate.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/calendars")
public class CalendarController {

    private final ModelMapper modelMapper;
    private final UserService userService;
    CalendarService calendarService;

    @GetMapping("/{calendarId}")
    public ResponseEntity<CalendarDto> getCalendar(@PathVariable Long calendarId) {
        Calendar calendar = calendarService.getCalendar(calendarId);
        CalendarDto calendarDTO = modelMapper.map(calendar, CalendarDto.class);
        return new ResponseEntity<>(calendarDTO, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CalendarDto>> getCalendarsByUser(@PathVariable Long userId) {
        List<Calendar> calendars = calendarService.getCalendarsByUser(userId);
        List<CalendarDto> calendarsDTO = calendars.stream().map(calendar -> modelMapper.map(calendar, CalendarDto.class)).toList();
        return new ResponseEntity<>(calendarsDTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> createCalendar(@RequestBody CalendarCreationDto calendarDTO) {
        Calendar calendar = Calendar.builder()
                .calendarName(calendarDTO.getCalendarName())
                .user(userService.getUser(calendarDTO.getUserId())).build();
        calendarService.createCalendar(calendar);
        return new ResponseEntity<>("Calendar created successfully",HttpStatus.CREATED);
    }

    @DeleteMapping("/{calendarId}")
    public ResponseEntity<String> deleteCalendar(@PathVariable Long calendarId) {
        calendarService.deleteCalendar(calendarId);
        return new ResponseEntity<>("Calendar deleted successfully",HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{calendarId}")
    public ResponseEntity<String> updateCalendar(@PathVariable Long calendarId, @RequestBody CalendarCreationDto calendarDTO) {
        Calendar calendar = modelMapper.map(calendarDTO, Calendar.class);
        calendarService.updateCalendar(calendarId, calendar);
        return new ResponseEntity<>("Calendar updated successfully",HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<CalendarDto>> getAllCalendars() {
        List<CalendarDto> calendarsDTO = calendarService.getAllCalendars().stream().map(calendar -> modelMapper.map(calendar, CalendarDto.class)).toList();
        return new ResponseEntity<>(calendarsDTO, HttpStatus.OK);
    }

}
