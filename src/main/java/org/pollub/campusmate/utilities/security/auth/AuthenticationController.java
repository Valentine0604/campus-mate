package org.pollub.campusmate.utilities.security.auth;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.pollub.campusmate.user.dto.ChangePasswordDto;
import org.pollub.campusmate.user.dto.UserCreationDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody UserCreationDto request, HttpServletResponse servletResponse) throws NoSuchAlgorithmException {
        AuthenticationResponse authResponse = authenticationService.register(request, servletResponse);
        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request, HttpServletResponse servletResponse) {
        AuthenticationResponse authResponse = authenticationService.authenticate(request, servletResponse);
        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse servletResponse) {
        authenticationService.logout(servletResponse);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/change-password")
    public ResponseEntity<Void> changePassword(@Valid @RequestBody ChangePasswordDto request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            bindingResult.getAllErrors().forEach(error -> errorMessage.append(error.getDefaultMessage()).append("\n"));
            return ResponseEntity.badRequest().build();
        }

        authenticationService.changePassword(request.getEmail(), request.getNewPassword());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
