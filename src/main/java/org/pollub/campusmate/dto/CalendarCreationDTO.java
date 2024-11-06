package org.pollub.campusmate.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.pollub.campusmate.entity.User;

@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class CalendarCreationDTO {

    private final String calendarName;
    private final User user;
}
