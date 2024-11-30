package org.pollub.campusmate.utilities.security.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {

    @NotBlank(message = "Email cannot be empty")
    private String email;

    @NotBlank(message = "Password cannot be empty")
    private String password;
}
