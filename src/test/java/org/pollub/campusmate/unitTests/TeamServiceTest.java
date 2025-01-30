package org.pollub.campusmate.unitTests;

import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.pollub.campusmate.team.entity.Team;
import org.pollub.campusmate.team.exception.TeamNotFound;
import org.pollub.campusmate.team.repository.TeamRepository;
import org.pollub.campusmate.team.service.TeamService;
import org.pollub.campusmate.user.entity.User;
import org.pollub.campusmate.user.repository.UserRepository;
import org.pollub.campusmate.utilities.security.Role;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TeamServiceTest {
    @Mock
    private TeamRepository teamRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TeamService teamService;

    private Team testTeam;
    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = User.builder()
                .userId(1L)
                .email("test@test.com")
                .firstName("Test")
                .lastName("User")
                .password("password")
                .role(Role.ROLE_STUDENT)
                .build();

        testTeam = new Team();
        testTeam.setTeamId(1L);
        testTeam.setTeamName("Test Team");
        testTeam.setDescription("Test Description");
        testTeam.setCreatorId(1L);
        testTeam.setUsers(new HashSet<>());
        testTeam.setPosts(new ArrayList<>());
        testTeam.setEvents(new ArrayList<>());
    }

    @Test
    void getTeam_ShouldReturnTeam() {
        when(teamRepository.findById(1L)).thenReturn(Optional.of(testTeam));
        Team result = teamService.getTeam(1L);
        assertNotNull(result);
        assertEquals("Test Team", result.getTeamName());
    }

    @Test
    void addTeam_ShouldSaveTeam() {
        when(teamRepository.save(any(Team.class))).thenReturn(testTeam);
        Team result = teamService.addTeam(testTeam);
        assertNotNull(result);
        verify(teamRepository).save(testTeam);
    }

    @Test
    void addTeamUser_ShouldAddUserToTeam() {
        when(teamRepository.existsById(1L)).thenReturn(true);
        when(teamRepository.findById(1L)).thenReturn(Optional.of(testTeam));
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(teamRepository.save(any(Team.class))).thenReturn(testTeam);
        teamService.addTeamUser(1L, 1L);
        verify(teamRepository).save(any(Team.class));
        assertTrue(testTeam.getUsers().contains(testUser));
    }

    @Test
    void addTeamUser_ShouldThrowException_WhenTeamNotFound() {
        when(teamRepository.existsById(1L)).thenReturn(false);
        assertThrows(TeamNotFound.class, () -> teamService.addTeamUser(1L, 1L));
    }

    @Test
    void removeUserFromTeam_ShouldRemoveUser() {
        testTeam.getUsers().add(testUser);
        when(teamRepository.existsById(1L)).thenReturn(true);
        when(teamRepository.findById(1L)).thenReturn(Optional.of(testTeam));
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        teamService.removeUserFromTeam(1L, 1L);
        verify(teamRepository).save(testTeam);
        assertFalse(testTeam.getUsers().contains(testUser));
    }

    @Test
    void removeUserFromTeam_ShouldThrowException_WhenTeamNotFound() {
        when(teamRepository.existsById(1L)).thenReturn(false);
        assertThrows(TeamNotFound.class, () -> teamService.removeUserFromTeam(1L, 1L));
    }

    @Test
    void deleteTeam_ShouldDeleteTeam() {
        when(teamRepository.existsById(1L)).thenReturn(true);
        when(teamRepository.findById(1L)).thenReturn(Optional.of(testTeam));
        teamService.deleteTeam(1L);
        verify(teamRepository).deleteById(1L);
    }

    @Test
    void updateTeam_ShouldUpdateTeam() {
        when(teamRepository.existsById(1L)).thenReturn(true);
        when(teamRepository.findById(1L)).thenReturn(Optional.of(testTeam));
        Team updatedTeam = new Team();
        updatedTeam.setTeamName("Updated Team");
        updatedTeam.setDescription("Updated Description");
        teamService.updateTeam(1L, updatedTeam);
        verify(teamRepository).save(any(Team.class));
    }

    @Test
    void getAllTeams_ShouldReturnAllTeams() {
        when(teamRepository.findAll()).thenReturn(List.of(testTeam));
        List<Team> result = teamService.getAllTeams();
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    @Test
    void getTeamsByCreatorId_ShouldReturnTeams() {
        when(teamRepository.findAllByCreatorId(1L)).thenReturn(List.of(testTeam));
        Collection<Team> result = teamService.getTeamsByCreatorId(1L);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }
}

