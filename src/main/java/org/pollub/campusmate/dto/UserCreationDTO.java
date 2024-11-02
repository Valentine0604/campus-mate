package org.pollub.campusmate.dto;


import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.pollub.campusmate.validator.ValidPassword;

@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class UserCreationDTO {
    private final String email;
    private final String firstName;
    private final String lastName;

    @NonNull
    @Enumerated(EnumType.STRING)
    private final String role;

    @ValidPassword
    private final String password;

}
