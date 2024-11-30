package org.pollub.campusmate.utilities.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.pollub.campusmate.user.dto.ChangePasswordDto;

public class PasswordMatchValidator implements ConstraintValidator<ValidPasswordMatch, ChangePasswordDto> {
    @Override
    public boolean isValid(ChangePasswordDto password, ConstraintValidatorContext context) {
        return password.getNewPassword().equals(password.getConfirmPassword());
    }
}
