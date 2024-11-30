package org.pollub.campusmate.utilities.security.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import org.pollub.campusmate.user.dto.UserCreationDto;
import org.pollub.campusmate.user.entity.User;
import org.pollub.campusmate.user.exception.UserNotFound;
import org.pollub.campusmate.user.repository.UserRepository;
import org.pollub.campusmate.utilities.security.config.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static org.pollub.campusmate.utilities.security.config.PasswdGenerator.generatePassword;
import org.pollub.campusmate.utilities.service.EmailSenderService;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final EmailSenderService emailSenderService;

    public AuthenticationResponse register(UserCreationDto request) throws NoSuchAlgorithmException {
        String rawPassword = generatePassword();

        System.out.println(rawPassword);

        if (rawPassword == null || rawPassword.isEmpty()) {
            throw new IllegalArgumentException("Generated password cannot be null");
        }

        var createdUser = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(rawPassword)
                .role(request.getRole())
                .isFirstPasswordChanged(false)
                .build();

        String encodedPassword = passwordEncoder.encode(rawPassword);

        createdUser.setPassword(encodedPassword);

        userRepository.save(createdUser);

        var jwtToken = jwtService.generateToken(createdUser);

        emailSenderService.sendEmail(
                createdUser.getEmail(),
                "Welcome to CampusMate",
                "Your credentials are as follows: \nEmail: " + createdUser.getEmail() + "\nPassword: " + rawPassword + "\n\nPlease change your password after logging in."
                + "\n\nBest regards,\nCampusMate Team"
        );

        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserNotFound("User with email " + request.getEmail() + " not found"));

        if(!user.isFirstPasswordChanged()){
            throw new IllegalArgumentException("Generated password must be changed");
        }

        var jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    public void changePassword(String email, String newPassword) {
        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFound("User with email " + email + " not found"));

        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);

        user.setFirstPasswordChanged(true);

        userRepository.save(user);
    }
}
