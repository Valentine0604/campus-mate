package org.pollub.campusmate.user.dto;


import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.pollub.campusmate.utilities.security.Role;


@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class UserDto {

    private final Long userId;

    @Email
    @NonNull
    private final String email;

    @NonNull
    private final String firstName;

    @NonNull
    private final String lastName;

    @NonNull
    @Enumerated(EnumType.STRING)
    private final Role role;

    private final String group;
}
