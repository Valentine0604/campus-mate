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

    @PostMapping("/create")
    public ResponseEntity<Calendar> createCalendar(@RequestBody Calendar calendar) {
        return new ResponseEntity<>(calendarService.createCalendar(calendar), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{calendarId}")
    public ResponseEntity<HttpStatus> deleteCalendar(@PathVariable Long calendarId) {
        calendarService.deleteCalendar(calendarId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Calendar>> getAllCalendars() {
        return new ResponseEntity<>(calendarService.getAllCalendars(), HttpStatus.OK);
    }

}