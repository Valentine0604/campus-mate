package org.pollub.campusmate.unitTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.pollub.campusmate.grade.dto.GradeDto;
import org.pollub.campusmate.grade.entity.Grade;
import org.pollub.campusmate.grade.exception.GradeNotFound;
import org.pollub.campusmate.grade.repository.GradeRepository;
import org.pollub.campusmate.grade.service.GradeService;
import org.pollub.campusmate.user.entity.User;
import org.pollub.campusmate.user.repository.UserRepository;
import org.pollub.campusmate.utilities.security.Role;


import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GradeServiceTest {
    @Mock
    private GradeRepository gradeRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private GradeService gradeService;

    private Grade testGrade;
    private GradeDto testGradeDto;
    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = User.builder()
                .userId(1L)
                .email("johndoe@up.edu")
                .firstName("John")
                .lastName("Doe")
                .password("password")
                .role(Role.ROLE_STUDENT)
                .grades(new ArrayList<>())
                .build();

        testGrade = new Grade();
        testGrade.setGradeId(1L);
        testGrade.setSubjectName("Math");
        testGrade.setGrade("A");
        testGrade.setComment("Excellent work");
        testGrade.setDateOfReceipt(LocalDate.now());
        testGrade.setUser(testUser);
        testGrade.setCreatorId(1L);

        testUser.getGrades().add(testGrade);

        testGradeDto = new GradeDto(
                1L,
                "Math",
                "A",
                "Excellent work",
                LocalDate.now()
        );
    }

    @Test
    void getGrade_ShouldReturnGrade() {
        when(gradeRepository.findById(1L)).thenReturn(Optional.of(testGrade));

        Grade result = gradeService.getGrade(1L);

        assertNotNull(result);
        assertEquals("Math", result.getSubjectName());
        assertEquals("A", result.getGrade());
    }

    @Test
    void getGrade_ShouldThrowException_WhenNotFound() {
        when(gradeRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(GradeNotFound.class, () -> gradeService.getGrade(999L));
    }

    @Test
    void getGradesByStudentId_ShouldReturnGrades() {
        when(gradeRepository.findByUserUserId(1L))
                .thenReturn(Arrays.asList(testGrade));

        List<Grade> results = gradeService.getGradesByStudentId(1L);

        assertFalse(results.isEmpty());
        assertEquals(1, results.size());
    }


    @Test
    void addGrade_ShouldSaveGrade() {
        when(gradeRepository.save(any(Grade.class))).thenReturn(testGrade);

        Grade result = gradeService.addGrade(testGrade);

        assertNotNull(result);
        verify(gradeRepository).save(testGrade);
    }

    @Test
    void updateGrade_ShouldUpdateExistingGrade() {
        when(gradeRepository.existsById(1L)).thenReturn(true);
        when(gradeRepository.findById(1L)).thenReturn(Optional.of(testGrade));

        gradeService.updateGrade(1L, testGradeDto);

        verify(gradeRepository).save(any(Grade.class));
    }

    @Test
    void deleteGrade_ShouldDeleteGrade() {
        when(gradeRepository.findById(1L)).thenReturn(Optional.of(testGrade));

        gradeService.deleteGrade(1L);

        verify(gradeRepository).delete(any(Grade.class));
    }

    @Test
    void getGradesByCreatorId_ShouldReturnGrades() {
        when(userRepository.existsById(2L)).thenReturn(true);
        when(gradeRepository.findAllByCreatorId(2L))
                .thenReturn(Arrays.asList(testGrade));

        Collection<Grade> results = gradeService.getGradesByCreatorId(2L);

        assertFalse(results.isEmpty());
        assertEquals(1, results.size());
    }

    @Test
    void getAllGrades_ShouldReturnAllGrades() {
        when(gradeRepository.findAll()).thenReturn(Arrays.asList(testGrade));

        List<Grade> results = gradeService.getAllGrades();

        assertFalse(results.isEmpty());
        assertEquals(1, results.size());
    }
}
