package org.pollub.campusmate.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.pollub.campusmate.validator.ValidPassword;

@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class ChangePasswordDTO {
    private final String oldPassword;
    @ValidPassword
    private final String newPassword;
    private final String confirmPassword;
}
