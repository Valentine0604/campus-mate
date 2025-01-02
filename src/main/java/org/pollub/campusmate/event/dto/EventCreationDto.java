package org.pollub.campusmate.event.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.pollub.campusmate.team.entity.Team;
import org.pollub.campusmate.utilities.validator.ValidDate;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
@ValidDate
public class EventCreationDto {

    private final String eventName;
    private final String eventDescription;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final Team team;
}
