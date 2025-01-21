package org.pollub.campusmate.schedule.dto;

import lombok.Data;
import org.pollub.campusmate.schedule.entity.CalendarEvent;

import java.util.List;

@Data
public class CalendarResponse {
    private String calendarName;
    private String timezone;
    private List<CalendarEvent> events;
}
