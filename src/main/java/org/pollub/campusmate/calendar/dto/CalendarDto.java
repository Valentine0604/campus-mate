package org.pollub.campusmate.calendar.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class CalendarDto {

    private final Long calendarId;
    private final String calendarName;

}
