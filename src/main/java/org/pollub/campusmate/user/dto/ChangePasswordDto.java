package org.pollub.campusmate.user.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.pollub.campusmate.utilities.validator.ValidOldPassword;
import org.pollub.campusmate.utilities.validator.ValidPassword;
import org.pollub.campusmate.utilities.validator.ValidPasswordMatch;

@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
@ValidPasswordMatch
@ValidOldPassword
public class ChangePasswordDto {

    @NonNull
    @Email
    private final String email;

    @NonNull
    @Size(min = 6, max = 12)
    private final String oldPassword;

    @ValidPassword
    @Size(min = 6, max = 12)
    private final String newPassword;

    @NonNull
    @Size(min = 6, max = 12)
    private final String confirmPassword;
}
