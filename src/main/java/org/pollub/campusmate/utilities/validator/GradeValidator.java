package org.pollub.campusmate.utilities.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class GradeValidator implements ConstraintValidator<ValidGrade, String> {

    @Override
    public boolean isValid(String grade, ConstraintValidatorContext context) {
        if(grade == null) {
            return false;
        }
        try{
            int gradeValue = Integer.parseInt(grade);
            return gradeValue >= 2 && gradeValue <= 5;
        } catch(NumberFormatException e) {
            return false;
        }
    }
}
