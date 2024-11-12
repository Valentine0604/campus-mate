package org.pollub.campusmate.utilities.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.pollub.campusmate.user.service.UserService;

import java.util.List;

public class OldPasswordValidator implements ConstraintValidator<ValidOldPassword, String> {

    private UserService userService;

    @Override
    public void initialize(ValidOldPassword annotation) {
    }

    @Override
    public boolean isValid(String newPassword, ConstraintValidatorContext context) {
        // Get the current user's password history
        List<String> passwordHistory = userService.getCurrentPassword();

        // Check if the new password is the same as any of the previous passwords
        return !passwordHistory.contains(newPassword);
    }
}
