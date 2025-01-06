package org.pollub.campusmate.team.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class TeamDto {
    private final Long teamId;
    private final String teamName;
    private final String description;
}
