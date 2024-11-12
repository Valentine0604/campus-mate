package org.pollub.campusmate.utilities.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.pollub.campusmate.event.entity.Event;

public class DateValidator implements ConstraintValidator<ValidDate, Event> {

    @Override
    public boolean isValid(Event event, ConstraintValidatorContext context) {

        return event.getStartDate().isBefore(event.getEndDate());
    }
}
