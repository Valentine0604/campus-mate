package org.pollub.campusmate.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.pollub.campusmate.constants.Constants;

public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {



    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        return Constants.PASSWORD_PATTERN.matcher(password).matches();
    }
}
