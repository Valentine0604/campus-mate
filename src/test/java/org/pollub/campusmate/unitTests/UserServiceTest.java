package org.pollub.campusmate.unitTests;

import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.pollub.campusmate.post.mapper.PostMapper;
import org.pollub.campusmate.team.mapper.TeamMapper;
import org.pollub.campusmate.user.dto.UserDto;
import org.pollub.campusmate.user.entity.User;
import org.pollub.campusmate.user.repository.UserRepository;
import org.pollub.campusmate.user.service.UserService;
import org.pollub.campusmate.utilities.security.Role;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private PostMapper postMapper;

    @Mock
    private TeamMapper teamMapper;

    @InjectMocks
    private UserService userService;

    private User testUser;
    private UserDto testUserDto;

    @BeforeEach
    void setUp() {
        testUser = User.builder()
                .userId(1L)
                .email("test@test.com")
                .firstName("Test")
                .lastName("User")
                .password("encoded_password")
                .role(Role.ROLE_STUDENT)
                .build();

        testUserDto = new UserDto(1L, "test@test.com", "Test", "User", Role.ROLE_STUDENT, "Group A");
    }

    @Test
    void getUser_ShouldReturnUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));

        User result = userService.getUser(1L);

        assertNotNull(result);
        assertEquals(testUser.getEmail(), result.getEmail());
    }

    @Test
    void getUsersByRole_ShouldReturnUsers() {
        when(userRepository.findByRole(Role.ROLE_STUDENT))
                .thenReturn(List.of(testUser));

        List<User> result = userService.getUsersByRole(Role.ROLE_STUDENT);

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    @Test
    void updateUser_ShouldUpdateFields() {
        when(userRepository.existsById(1L)).thenReturn(true);
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(userRepository.existsByEmail(anyString())).thenReturn(false);

        userService.updateUser(1L, testUserDto);

        verify(userRepository).save(any(User.class));
    }

    @Test
    void deleteUser_ShouldDeleteUser() {
        when(userRepository.existsById(1L)).thenReturn(true);

        userService.deleteUser(1L);

        verify(userRepository).deleteById(1L);
    }
}
