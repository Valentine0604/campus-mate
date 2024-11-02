package org.pollub.campusmate.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.pollub.campusmate.entity.Team;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class PostCreationDTO {
    private final String postTitle;
    private final String postContent;
    private final List<Team> teams;
}
