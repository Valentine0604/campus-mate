package org.pollub.campusmate.utilities.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.pollub.campusmate.utilities.constants.Pattern;

import java.util.Optional;
import java.util.regex.Matcher;

public class PhoneNumberValidator implements ConstraintValidator<ValidPhoneNumber, String> {

    @Override
    public boolean isValid(String phone, ConstraintValidatorContext context) {
        return Optional.ofNullable(phone)
                .map(Pattern.PHONE_NUMBER_PATTERN::matcher)
                .map(Matcher::matches)
                .orElse(false);
    }
}
