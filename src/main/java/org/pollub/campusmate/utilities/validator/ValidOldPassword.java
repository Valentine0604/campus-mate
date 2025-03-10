package org.pollub.campusmate.utilities.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = OldPasswordValidator.class)
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidOldPassword {
    String message() default "New password cannot be the same as the new one";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}