// Model classes remain the same as before

// Controller
package com.example.calendar.controller;


import org.pollub.campusmate.schedule.dto.CalendarResponse;
import org.pollub.campusmate.schedule.entity.CalendarEvent;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.component.VEvent;
import org.springframework.web.client.RestTemplate;

import java.io.StringReader;
import java.time.ZonedDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/calendar")
public class CalendarController {

    private static final String CALENDAR_URL = "http://planwe.pollub.pl/plan.php?type=0&id=9926&cvsfile=true&wd=5";

    @GetMapping("/schedule")
    public ResponseEntity<CalendarResponse> getSchedule() {
        try {
            // Pobierz plik ICS
            RestTemplate restTemplate = new RestTemplate();
            String icsData = restTemplate.getForObject(CALENDAR_URL, String.class);

            // Parsuj dane ICS
            CalendarBuilder builder = new CalendarBuilder();
            Calendar calendar = builder.build(new StringReader(icsData));

            CalendarResponse response = new CalendarResponse();
            response.setCalendarName(calendar.getProperty("X-WR-CALNAME").getValue());
            response.setTimezone(calendar.getProperty("X-WR-TIMEZONE").getValue());

            List<CalendarEvent> events = new ArrayList<>();

            for (Object eventObj : calendar.getComponents("VEVENT")) {
                VEvent event = (VEvent) eventObj;
                CalendarEvent calEvent = new CalendarEvent();

                // Konwersja czasu UTC na strefę czasową z kalendarza
                ZoneId zoneId = ZoneId.of(response.getTimezone());
                calEvent.setStartTime(ZonedDateTime.ofInstant(
                        event.getStartDate().getDate().toInstant(),
                        zoneId
                ));
                calEvent.setEndTime(ZonedDateTime.ofInstant(
                        event.getEndDate().getDate().toInstant(),
                        zoneId
                ));

                calEvent.setUid(event.getUid().getValue());
                calEvent.setSummary(event.getSummary().getValue());
                calEvent.setStatus(event.getProperty("STATUS") != null ?
                        event.getProperty("STATUS").getValue() : null);

                // Wyciągnij lokalizację z podsumowania (ostatnie słowo)
                String summary = event.getSummary().getValue();
                if (summary.contains(" ")) {
                    String[] parts = summary.split(" ");
                    calEvent.setLocation(parts[parts.length - 1]);
                }

                events.add(calEvent);
            }

            response.setEvents(events);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }
}
