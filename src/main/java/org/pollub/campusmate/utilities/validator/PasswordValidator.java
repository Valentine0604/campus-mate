package org.pollub.campusmate.utilities.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.pollub.campusmate.utilities.constants.Pattern;

public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {



    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        return Pattern.PASSWORD_PATTERN.matcher(password).matches();
    }
}
