package org.pollub.campusmate.grade.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.pollub.campusmate.utilities.validator.ValidGrade;


@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class GradeCreationDto {

    private final String subjectName;
    @ValidGrade
    private final String grade;
    private final String comment;
    private final Long userId;
    private final Long creatorId;
}
