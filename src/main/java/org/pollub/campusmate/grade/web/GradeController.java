package org.pollub.campusmate.grade.web;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.pollub.campusmate.grade.dto.GradeCreationDto;
import org.pollub.campusmate.grade.dto.GradeDto;
import org.pollub.campusmate.grade.service.GradeService;
import org.pollub.campusmate.grade.entity.Grade;
import org.pollub.campusmate.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/grade")
public class GradeController {

    private final ModelMapper modelMapper;
    GradeService gradeService;
    UserService userService;

    @GetMapping("/{gradeId}")
    public ResponseEntity<GradeDto> getGrade(@PathVariable Long gradeId) {
        Grade grade = gradeService.getGrade(gradeId);
        GradeDto gradeDTO = modelMapper.map(grade, GradeDto.class);
        return new ResponseEntity<>(gradeDTO, HttpStatus.OK);
    }

    @GetMapping("/subject/{subjectName}")
    public ResponseEntity<List<GradeDto>> getGradesBySubjectName(@PathVariable String subjectName) {
        List<Grade> grades = gradeService.getGradesBySubjectName(subjectName);
        List<GradeDto> gradeDTOs = grades.stream().map(grade -> modelMapper.map(grade, GradeDto.class)).toList();
        return new ResponseEntity<>(gradeDTOs, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> createGrade(@Valid @RequestBody GradeCreationDto gradeDTO, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            bindingResult.getAllErrors().forEach(error -> errorMessage.append(error.getDefaultMessage()).append("\n"));
            return new ResponseEntity<>(errorMessage.toString(), HttpStatus.BAD_REQUEST);
        }
        Grade grade = modelMapper.map(gradeDTO, Grade.class);
        grade.setUser(userService.getUser(gradeDTO.getUserId()));
        gradeService.addGrade(grade);
        return new ResponseEntity<>("Grade added successfully",HttpStatus.CREATED);
    }

    @DeleteMapping("/{gradeId}")
    public ResponseEntity<String> deleteGrade(@PathVariable Long gradeId) {
        gradeService.deleteGrade(gradeId);
        return new ResponseEntity<>("Grade deleted successfully",HttpStatus.NO_CONTENT);
    }

    @PutMapping
    public ResponseEntity<String> updateGrade(@RequestParam Long gradeId, @Valid @RequestBody GradeDto gradeDTO, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            bindingResult.getAllErrors().forEach(error -> errorMessage.append(error.getDefaultMessage()).append("\n"));
            return new ResponseEntity<>(errorMessage.toString(), HttpStatus.BAD_REQUEST);
        }
        Grade grade = modelMapper.map(gradeDTO, Grade.class);
        gradeService.updateGrade(gradeId, grade);
        return new ResponseEntity<>("Grade updated successfully",HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<GradeDto>> getAllGrades() {
        return new ResponseEntity<>(gradeService.getAllGrades().stream().map(grade -> modelMapper.map(grade, GradeDto.class)).toList(), HttpStatus.OK);
    }
}
