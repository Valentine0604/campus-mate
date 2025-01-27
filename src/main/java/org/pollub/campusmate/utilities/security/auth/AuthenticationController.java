package org.pollub.campusmate.utilities.security.auth;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.pollub.campusmate.user.dto.ChangePasswordDto;
import org.pollub.campusmate.user.dto.UserDto;
import org.pollub.campusmate.user.entity.User;
import org.pollub.campusmate.user.exception.UserNotFound;
import org.pollub.campusmate.user.repository.UserRepository;
import org.pollub.campusmate.utilities.service.EmailSenderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailSenderService emailSenderService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserDto request, HttpServletResponse servletResponse) {
        String message = authenticationService.register(request, servletResponse);
        return ResponseEntity.ok(message);
    }

    @PostMapping("/login")
    public ResponseEntity<String> authenticate(@RequestBody AuthenticationRequest request, HttpServletResponse servletResponse) {
        AuthenticationResponse authResponse = authenticationService.authenticate(request, servletResponse);
        return new ResponseEntity<>("Logged in successfully", HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletResponse servletResponse) {
        authenticationService.logout(servletResponse);
        return new ResponseEntity<>("Logged out successfully", HttpStatus.OK);
    }

    @PostMapping("/change-password/{userId}")
    public ResponseEntity<String> changePassword(@PathVariable Long userId, @Valid @RequestBody ChangePasswordDto request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            bindingResult.getAllErrors().forEach(error -> errorMessage.append(error.getDefaultMessage()).append("\n"));
            return ResponseEntity.badRequest().body(errorMessage.toString());
        }

        User selectedUser = userRepository.findById(userId).orElseThrow(() -> new UserNotFound("User with id " + userId + " not found"));


        if (!passwordEncoder.matches(request.getOldPassword(), selectedUser.getPassword())) {
            return ResponseEntity.badRequest().body("Old password is incorrect");
        } else if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            return ResponseEntity.badRequest().body("New and confirm passwords don't match");
        }

        authenticationService.changePassword(request.getEmail(), request.getNewPassword());
        emailSenderService.sendEmail(selectedUser.getEmail(), "Your password has been changed",
                "Hello " + selectedUser.getFirstName() + " " + selectedUser.getLastName() +
                        ",\n" + "Your password has been successfully changed. If you did not request this change, please contact CampusMate support" +
                        "." + "\n\n" + "Best regards,\n" + "CampusMate team");

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
