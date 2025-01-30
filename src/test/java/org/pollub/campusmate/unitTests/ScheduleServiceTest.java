package org.pollub.campusmate.unitTests;

import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.pollub.campusmate.schedule.entity.Schedule;
import org.pollub.campusmate.schedule.repository.ScheduleRepository;
import org.pollub.campusmate.schedule.service.ScheduleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ScheduleServiceTest {
    @Mock
    private ScheduleRepository scheduleRepository;

    @InjectMocks
    private ScheduleService scheduleService;

    @Mock
    private MultipartFile mockFile;

    private Schedule testSchedule;

    @BeforeEach
    void setUp() {
        testSchedule = new Schedule();
        testSchedule.setSubjectName("Test Subject");
        testSchedule.setGroup("Test Group");
        testSchedule.setStartTime(LocalDateTime.now());
        testSchedule.setEndTime(LocalDateTime.now().plusHours(1));
    }

    @Test
    void deleteScheduleByGroupName_ShouldDeleteSchedule() {
        String groupName = "TestGroup";
        when(scheduleRepository.existsByGroup(groupName)).thenReturn(true);

        ResponseEntity<String> response = scheduleService.deleteScheduleByGroupName(groupName);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Schedule deleted successfully", response.getBody());
        verify(scheduleRepository).deleteScheduleByGroup(groupName);
    }

    @Test
    void deleteScheduleByGroupName_ShouldReturnNotFound() {
        String groupName = "NonExistentGroup";
        when(scheduleRepository.existsByGroup(groupName)).thenReturn(false);

        ResponseEntity<String> response = scheduleService.deleteScheduleByGroupName(groupName);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void getSchedule_ShouldReturnAllSchedules() {
        List<Schedule> schedules = List.of(testSchedule);
        when(scheduleRepository.findAll()).thenReturn(schedules);

        Iterable<Schedule> result = scheduleService.getSchedule();

        assertNotNull(result);
        assertTrue(StreamSupport.stream(result.spliterator(), false)
                .anyMatch(s -> s.getSubjectName().equals("Test Subject")));
    }
}
