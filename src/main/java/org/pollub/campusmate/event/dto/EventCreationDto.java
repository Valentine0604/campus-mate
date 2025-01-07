package org.pollub.campusmate.event.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class EventCreationDto {

    private final String eventName;
    private final String eventDescription;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final Long teamId;
}
