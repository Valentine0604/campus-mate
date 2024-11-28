package org.pollub.campusmate.utilities.security.auth;

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
import static org.pollub.campusmate.utilities.service.EmailSenderService.sendEmail;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(UserCreationDto request) throws NoSuchAlgorithmException {
        String rawPassword = generatePassword();

        if(rawPassword == null || rawPassword.isEmpty()){
            throw new IllegalArgumentException("Generated password cannot be null");
        }

        String encodedPassword = passwordEncoder.encode(rawPassword);

        var createdUser = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(encodedPassword)
                .role(request.getRole())
                .build();

        userRepository.save(createdUser);
        var jwtToken = jwtService.generateToken(createdUser);

        sendEmail(createdUser.getEmail(), "Welcome to CampusMate", "Your credentials are as follows: \nEmail: " + createdUser.getEmail() + "\nPassword: " + createdUser.getPassword() + "\n\nPlease change your password after logging in.");

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

        var jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder().token(jwtToken).build();
    }
}
