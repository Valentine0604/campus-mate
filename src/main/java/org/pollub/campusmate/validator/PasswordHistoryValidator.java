package org.pollub.campusmate.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.pollub.campusmate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class PasswordHistoryValidator implements ConstraintValidator<PasswordHistoryValidation, String> {

    private UserService userService;

    @Override
    public void initialize(PasswordHistoryValidation annotation) {
    }

    @Override
    public boolean isValid(String newPassword, ConstraintValidatorContext context) {
        // Get the current user's password history
        List<String> passwordHistory = userService.getCurrentPassword();

        // Check if the new password is the same as any of the previous passwords
        return !passwordHistory.contains(newPassword);
    }
}
