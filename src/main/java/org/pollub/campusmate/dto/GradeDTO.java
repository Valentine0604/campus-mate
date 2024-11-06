package org.pollub.campusmate.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.pollub.campusmate.validator.ValidGrade;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class GradeDTO {
    private final String subjectName;
    @ValidGrade
    private final String grade;
    private final String comment;
    private final LocalDate dateOfReceipt;
}
