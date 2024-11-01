package org.pollub.campusmate.web;

import lombok.AllArgsConstructor;
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

    CalendarService calendarService;

    @GetMapping("{calendarId}")
    public ResponseEntity<Calendar> getCalendar(@PathVariable Long calendarId) {
        return new ResponseEntity<>(calendarService.getCalendar(calendarId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> createCalendar(@RequestBody Calendar calendar) {
        calendarService.createCalendar(calendar);
        return new ResponseEntity<>("Calendar created successfully",HttpStatus.CREATED);
    }

    @DeleteMapping("/{calendarId}")
    public ResponseEntity<String> deleteCalendar(@PathVariable Long calendarId) {
        calendarService.deleteCalendar(calendarId);
        return new ResponseEntity<>("Calendar deleted successfully",HttpStatus.NO_CONTENT);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Calendar>> getAllCalendars() {
        return new ResponseEntity<>(calendarService.getAllCalendars(), HttpStatus.OK);
    }

}
