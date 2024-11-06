package org.pollub.campusmate.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.pollub.campusmate.entity.User;



@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class TeamDTO {
    private final String teamName;
    private final String description;
}
