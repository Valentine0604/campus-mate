package org.pollub.campusmate.user.dto;


import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
    private final String email;
    private final String firstName;
    private final String lastName;

    @NonNull
    @Enumerated(EnumType.STRING)
    private final Role role;

    @ValidPassword
    private final String password;

}
