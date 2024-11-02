package org.pollub.campusmate.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.pollub.campusmate.Role;


@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class UserDTO {
    private final String email;
    private final String firstName;
    private final String lastName;
    private final Role role;


}
