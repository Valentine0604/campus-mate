package org.pollub.campusmate.utilities.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.pollub.campusmate.user.dto.ChangePasswordDto;
import org.pollub.campusmate.user.service.UserService;

import java.util.List;

public class OldPasswordValidator implements ConstraintValidator<ValidOldPassword, ChangePasswordDto> {

    @Override
    public boolean isValid(ChangePasswordDto password, ConstraintValidatorContext context) {
        return !password.getNewPassword().equals(password.getOldPassword());
    }
}
