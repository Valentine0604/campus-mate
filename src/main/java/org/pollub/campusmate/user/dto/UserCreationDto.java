package org.pollub.campusmate.user.dto;


import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.pollub.campusmate.utilities.security.Role;
import org.pollub.campusmate.utilities.validator.ValidPassword;

@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class UserCreationDto {

    @Email
    private final String email;

    @NonNull
    private final String firstName;

    @NonNull
    private final String lastName;

    @NonNull
    @Enumerated(EnumType.STRING)
    private final Role role;
}
