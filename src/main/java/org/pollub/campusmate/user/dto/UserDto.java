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
    private final String email;

    private final String firstName;

    private final String lastName;

    @Enumerated(EnumType.STRING)
    private final Role role;

    private final String group;
}
