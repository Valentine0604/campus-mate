package org.pollub.campusmate.utilities.security.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
    private String token;
    private boolean twoFactorRequired;
    private String message; //Enter otp to complete login
    private List<String> backupCodes;
}
