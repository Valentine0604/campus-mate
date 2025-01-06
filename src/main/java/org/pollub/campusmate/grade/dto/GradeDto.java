package org.pollub.campusmate.grade.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.pollub.campusmate.utilities.validator.ValidGrade;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class GradeDto {
    private final Long gradeId;
    private final String subjectName;
    @ValidGrade
    private final String grade;
    private final String comment;
    private final LocalDate dateOfReceipt;
}
