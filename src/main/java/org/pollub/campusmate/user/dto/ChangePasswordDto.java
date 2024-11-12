package org.pollub.campusmate.user.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.pollub.campusmate.utilities.validator.ValidPassword;

@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class ChangePasswordDto {
    private final String oldPassword;
    @ValidPassword
    private final String newPassword;
    private final String confirmPassword;
}
