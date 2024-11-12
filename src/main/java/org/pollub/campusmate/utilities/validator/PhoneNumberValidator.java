package org.pollub.campusmate.utilities.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.pollub.campusmate.utilities.constants.Pattern;

public class PhoneNumberValidator implements ConstraintValidator<ValidPhoneNumber, String> {

    @Override
    public boolean isValid(String phone, ConstraintValidatorContext context) {
        return Pattern.PHONE_NUMBER_PATTERN.matcher(phone).matches();
    }
}
