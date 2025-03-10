package org.pollub.campusmate.utilities.security.auth;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.pollub.campusmate.addressbookentry.entity.AddressBookEntry;
import org.pollub.campusmate.addressbookentry.service.AddressBookEntryService;
import org.pollub.campusmate.user.dto.UserDto;
import org.pollub.campusmate.user.entity.User;
import org.pollub.campusmate.user.exception.EmailAlreadyExistsException;
import org.pollub.campusmate.user.exception.UserNotFound;
import org.pollub.campusmate.user.repository.UserRepository;
import org.pollub.campusmate.utilities.security.Role;
import org.pollub.campusmate.utilities.security.config.JwtService;
import org.pollub.campusmate.utilities.service.EmailSenderService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

import static org.pollub.campusmate.utilities.security.config.PasswdGenerator.generatePassword;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final AddressBookEntryService addressBookEntryService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final EmailSenderService emailSenderService;

    @Value("${ADMIN_EMAIL}")
    private String adminEmail;

    @Value("${ADMIN_PASSWORD}")
    private String adminPassword;

    public String register(UserDto request, HttpServletResponse response) {
        String rawPassword = generatePassword();

        if (rawPassword == null || rawPassword.isEmpty()) {
            throw new IllegalArgumentException("Generated password cannot be null");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException("User with email " + request.getEmail() + " already exists");
        }

        String encodedPassword = passwordEncoder.encode(rawPassword);

        var createdUser = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(encodedPassword)
                .role(request.getRole())
                .group(request.getGroup())
                .isFirstPasswordChanged(false)
                .group(request.getGroup())
                .build();

        var savedUser = userRepository.save(createdUser);

        if (savedUser.getRole().equals(Role.ROLE_LECTURER)) {
            String contactName = savedUser.getFirstName() + " " + savedUser.getLastName();
            AddressBookEntry entry = new AddressBookEntry(contactName, savedUser.getEmail(), savedUser);
            addressBookEntryService.saveAddressBookEntry(entry);
        }

        emailSenderService.sendEmail(
                savedUser.getEmail(),
                "Welcome to CampusMate",
                "Hello " + savedUser.getFirstName() + " " + savedUser.getLastName() + ",\n"
                        + "Your credentials are as follows: \nEmail: " + savedUser.getEmail()
                        + "\nPassword: " + rawPassword
                        + "\n\nPlease change your password after logging in."
                        + "\n\nBest regards,\nCampusMate Team");

        return "User registered successfully";
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

        if (!user.isFirstPasswordChanged()) {
            throw new IllegalArgumentException("Generated password must be changed");
        }
        var extraClaims = new HashMap<String, Object>();
        extraClaims.put("role", user.getRole());
        extraClaims.put("userId", user.getUserId());
        extraClaims.put("isFirstPasswordChanged", user.isFirstPasswordChanged());
        extraClaims.put("group", user.getGroup());
        var jwtToken = jwtService.generateToken(extraClaims, user);
        addJwtCookie(response, jwtToken);

        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    public void logout(HttpServletResponse response) {
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

    private void addJwtCookie(HttpServletResponse response, String token) {
        Cookie jwtCookie = new Cookie("jwt", token);
        jwtCookie.setHttpOnly(false);
        jwtCookie.setSecure(false);
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(24 * 60 * 60);
        jwtCookie.setDomain("localhost");
        response.addCookie(jwtCookie);
    }

    private void clearJwtCookie(HttpServletResponse response) {
        Cookie jwtCookie = new Cookie("jwt", null);
        jwtCookie.setHttpOnly(false);
        jwtCookie.setSecure(true);
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(5);

        response.addCookie(jwtCookie);
    }

    @PostConstruct
    public void initializeUsers() {

        if(!userRepository.existsByEmail(adminEmail)) {
            User user = new User();
            user.setEmail(adminEmail);
            user.setFirstName("admin");
            user.setLastName("admin");
            user.setPassword(passwordEncoder.encode(adminPassword));
            user.setRole(Role.valueOf("ROLE_ADMIN"));
            user.setFirstPasswordChanged(true);
            userRepository.save(user);
        }
    }
}
