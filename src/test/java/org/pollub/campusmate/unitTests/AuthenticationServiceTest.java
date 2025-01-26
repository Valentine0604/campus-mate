package org.pollub.campusmate.unitTests;

import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import org.pollub.campusmate.user.dto.UserDto;
import org.pollub.campusmate.user.entity.User;
import org.pollub.campusmate.user.exception.EmailAlreadyExistsException;
import org.pollub.campusmate.user.repository.UserRepository;
import org.pollub.campusmate.utilities.security.Role;
import org.pollub.campusmate.utilities.security.auth.AuthenticationRequest;
import org.pollub.campusmate.utilities.security.auth.AuthenticationResponse;
import org.pollub.campusmate.utilities.security.auth.AuthenticationService;
import org.pollub.campusmate.utilities.security.config.JwtService;
import org.pollub.campusmate.utilities.service.EmailSenderService;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private EmailSenderService emailSenderService;

    @Mock
    private HttpServletResponse response;

    @InjectMocks
    private AuthenticationService authenticationService;

    private UserDto testUserDto;
    private User testUser;
    private AuthenticationRequest testAuthRequest;

    @BeforeEach
    void setUp() {
        testUserDto = new UserDto(1L, "test@test.com", "Test", "User", Role.ROLE_STUDENT, "Group A");
        testUser = User.builder()
                .userId(1L)
                .email("test@test.com")
                .firstName("Test")
                .lastName("User")
                .password("encoded_password")
                .role(Role.ROLE_STUDENT)
                .isFirstPasswordChanged(true)
                .build();
        testAuthRequest = new AuthenticationRequest("test@test.com", "password");
    }

    @Test
    void register_ShouldCreateNewUser() {
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("encoded_password");
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        String result = authenticationService.register(testUserDto, response);

        assertNotNull(result);
        assertEquals("User registered successfully", result);
        verify(emailSenderService).sendEmail(anyString(), anyString(), anyString());
    }

    @Test
    void register_ShouldThrowException_WhenEmailExists() {
        when(userRepository.existsByEmail(anyString())).thenReturn(true);

        assertThrows(EmailAlreadyExistsException.class,
                () -> authenticationService.register(testUserDto, response));
    }

    @Test
    void authenticate_ShouldReturnToken() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(testUser));
        when(jwtService.generateToken(any(), any())).thenReturn("jwt_token");

        AuthenticationResponse result = authenticationService.authenticate(testAuthRequest, response);

        assertNotNull(result);
        assertEquals("jwt_token", result.getToken());
    }

    @Test
    void changePassword_ShouldUpdatePassword() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(testUser));
        when(passwordEncoder.encode(anyString())).thenReturn("new_encoded_password");

        authenticationService.changePassword("test@test.com", "newPassword");

        verify(userRepository).save(any(User.class));
        assertTrue(testUser.isFirstPasswordChanged());
    }
}
