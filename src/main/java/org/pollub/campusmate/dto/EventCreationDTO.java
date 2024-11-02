package org.pollub.campusmate.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.pollub.campusmate.entity.Calendar;
import org.pollub.campusmate.entity.Team;
import org.pollub.campusmate.validator.ValidDate;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
@ValidDate
public class EventCreationDTO {

    private final String eventName;
    private final String eventDescription;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final Team team;
    private final Calendar calendar;
}
