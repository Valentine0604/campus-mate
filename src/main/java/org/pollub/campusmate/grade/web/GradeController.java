package org.pollub.campusmate.grade.web;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.pollub.campusmate.grade.dto.GradeCreationDto;
import org.pollub.campusmate.grade.dto.GradeDto;
import org.pollub.campusmate.grade.mapper.GradeCreationMapper;
import org.pollub.campusmate.grade.mapper.GradeMapper;
import org.pollub.campusmate.grade.service.GradeService;
import org.pollub.campusmate.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/api/grade")
public class GradeController {

    private final GradeMapper gradeMapper;
    private final GradeCreationMapper gradeCreationMapper;
    private final GradeService gradeService;
    private final UserService userService;

    @GetMapping("/{gradeId}")
    public ResponseEntity<GradeDto> getGrade(@PathVariable Long gradeId) {
        var grade = gradeService.getGrade(gradeId);
        var gradeDto = gradeMapper.toDto(grade);
        return new ResponseEntity<>(gradeDto, HttpStatus.OK);
    }

    @GetMapping("/subject/{subjectName}")
    public ResponseEntity<List<GradeDto>> getGradesBySubjectName(@PathVariable String subjectName) {
        var grades = gradeService.getGradesBySubjectName(subjectName).stream()
                .map(gradeMapper::toDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(grades, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> createGrade(
            @Valid @RequestBody GradeCreationDto gradeCreationDto,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getAllErrors().stream()
                    .map(error -> error.getDefaultMessage() + "\n")
                    .collect(Collectors.joining());
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }

        var grade = gradeCreationMapper.toEntity(gradeCreationDto);
        grade.setUser(userService.getUser(gradeCreationDto.getUserId()));
        gradeService.addGrade(grade);
        return new ResponseEntity<>("Grade added successfully", HttpStatus.CREATED);
    }

    @Transactional
    @DeleteMapping("/{gradeId}")
    public ResponseEntity<String> deleteGrade(@PathVariable Long gradeId) {
        gradeService.deleteGrade(gradeId);
        return new ResponseEntity<>("Grade deleted successfully", HttpStatus.OK);
    }

    @PatchMapping("/{gradeId}")
    public ResponseEntity<String> updateGrade(
            @PathVariable Long gradeId,
            @Valid @RequestBody GradeDto gradeDto,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getAllErrors().stream()
                    .map(error -> error.getDefaultMessage() + "\n")
                    .collect(Collectors.joining());
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }
        gradeService.updateGrade(gradeId, gradeDto);
        return new ResponseEntity<>("Grade updated successfully", HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<GradeDto>> getAllGrades() {
        var grades = gradeService.getAllGrades().stream()
                .map(gradeMapper::toDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(grades, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<GradeDto>> getGradesByUserId(@PathVariable Long userId) {
        var grades = gradeService.getGradesByUserId(userId).stream()
                .map(gradeMapper::toDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(grades, HttpStatus.OK);
    }

    @GetMapping("/creator/{creatorId}")
    public ResponseEntity<List<GradeDto>> getGradesByCreatorId(@PathVariable Long creatorId) {
        var grades = gradeService.getGradesByCreatorId(creatorId).stream()
                .map(gradeMapper::toDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(grades, HttpStatus.OK);
    }
}
