package org.pollub.campusmate.calendar.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.pollub.campusmate.user.entity.User;

@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class CalendarCreationDto {

    private final String calendarName;
    private final Long userId;
}
