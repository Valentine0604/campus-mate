package org.pollub.campusmate.utilities.security.auth;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;

import org.pollub.campusmate.user.dto.UserCreationDto;
import org.pollub.campusmate.user.entity.User;
import org.pollub.campusmate.user.exception.UserNotFound;
import org.pollub.campusmate.user.repository.UserRepository;
import org.pollub.campusmate.utilities.security.Role;
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

    public AuthenticationResponse register(UserCreationDto request, HttpServletResponse response) throws NoSuchAlgorithmException {
        String rawPassword = generatePassword();

        System.out.println(rawPassword);

        if (rawPassword == null || rawPassword.isEmpty()) {
            throw new IllegalArgumentException("Generated password cannot be null");
        }

        String encodedPassword = passwordEncoder.encode(rawPassword);

        var createdUser = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(encodedPassword)
                .role(request.getRole())
                .isFirstPasswordChanged(false)
                .build();

        userRepository.save(createdUser);

        var jwtToken = jwtService.generateToken(createdUser);
        addJwtCookie(response, jwtToken);

        emailSenderService.sendEmail(
                createdUser.getEmail(),
                "Welcome to CampusMate",
                "Hello " + createdUser.getFirstName() + " " + createdUser.getLastName() + ",\n"
                        + "Your credentials are as follows: \nEmail: " + createdUser.getEmail()
                        + "\nPassword: " + rawPassword
                        + "\n\nPlease change your password after logging in."
                        + "\n\nBest regards,\nCampusMate Team");

        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request, HttpServletResponse response) {
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
        var extraClaims = new HashMap<String, Object>();
        extraClaims.put("role", user.getRole());
        var jwtToken = jwtService.generateToken(extraClaims, user);
        addJwtCookie(response, jwtToken);

        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    public void logout(HttpServletResponse response){
        clearJwtCookie(response);
    }

    public void changePassword(String email, String newPassword) {
        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFound("User with email " + email + " not found"));

        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);

        user.setFirstPasswordChanged(true);



        userRepository.save(user);
    }

    private void addJwtCookie(HttpServletResponse response, String token){
        Cookie jwtCookie = new Cookie("jwt", token);
        jwtCookie.setHttpOnly(false);
        jwtCookie.setSecure(false);
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(24 * 60 * 60);
        jwtCookie.setDomain("localhost");
        response.addCookie(jwtCookie);
    }

    private void clearJwtCookie(HttpServletResponse response){
        Cookie jwtCookie = new Cookie("jwt", null);
        jwtCookie.setHttpOnly(false);
        jwtCookie.setSecure(true);
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(0);

        response.addCookie(jwtCookie);
    }

    @PostConstruct
    public void initializeUsers() {
        User user = new User();
        user.setEmail("admin@admin.pl");
        user.setPassword(passwordEncoder.encode("Admin1_"));
        user.setRole(Role.valueOf("ADMIN"));
        user.setFirstPasswordChanged(true);
        userRepository.save(user);
    }
}
