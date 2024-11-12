package org.pollub.campusmate.utilities.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = PasswordHistoryValidator.class)
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordHistoryValidation {
    String message() default "New password cannot be the same as the previous one";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}