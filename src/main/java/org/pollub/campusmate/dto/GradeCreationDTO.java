package org.pollub.campusmate.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.pollub.campusmate.entity.User;
import org.pollub.campusmate.validator.ValidGrade;


@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class GradeCreationDTO {

    private final String subjectName;
    @ValidGrade
    private final String grade;
    private final String comment;
    private final User user;
}
