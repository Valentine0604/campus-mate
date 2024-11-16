package org.pollub.campusmate.user.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.pollub.campusmate.utilities.security.Role;


@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class UserDto {
    private final String email;
    private final String firstName;
    private final String lastName;
    private final Role role;


}
