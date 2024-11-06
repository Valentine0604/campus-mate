package org.pollub.campusmate.web;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.pollub.campusmate.dto.GradeCreationDTO;
import org.pollub.campusmate.dto.GradeDTO;
import org.pollub.campusmate.entity.Grade;
import org.pollub.campusmate.service.GradeService;
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

    @GetMapping("/{gradeId}")
    public ResponseEntity<GradeDTO> getGrade(@PathVariable Long gradeId) {
        Grade grade = gradeService.getGrade(gradeId);
        GradeDTO gradeDTO = modelMapper.map(grade, GradeDTO.class);
        return new ResponseEntity<>(gradeDTO, HttpStatus.OK);
    }

    @GetMapping("/subject/{subjectName}")
    public ResponseEntity<List<GradeDTO>> getGradesBySubjectName(@PathVariable String subjectName) {
        List<Grade> grades = gradeService.getGradesBySubjectName(subjectName);
        List<GradeDTO> gradeDTOs = grades.stream().map(grade -> modelMapper.map(grade, GradeDTO.class)).toList();
        return new ResponseEntity<>(gradeDTOs, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> createGrade(@Valid @RequestBody GradeCreationDTO gradeDTO, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            bindingResult.getAllErrors().forEach(error -> errorMessage.append(error.getDefaultMessage()).append("\n"));
            return new ResponseEntity<>(errorMessage.toString(), HttpStatus.BAD_REQUEST);
        }
        Grade grade = modelMapper.map(gradeDTO, Grade.class);
        gradeService.addGrade(grade);
        return new ResponseEntity<>("Grade added successfully",HttpStatus.CREATED);
    }

    @DeleteMapping("/{gradeId}")
    public ResponseEntity<String> deleteGrade(@PathVariable Long gradeId) {
        gradeService.deleteGrade(gradeId);
        return new ResponseEntity<>("Grade deleted successfully",HttpStatus.NO_CONTENT);
    }

    @PutMapping
    public ResponseEntity<String> updateGrade(@RequestParam Long gradeId, @Valid @RequestBody GradeDTO gradeDTO, BindingResult bindingResult) {
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
    public ResponseEntity<List<GradeDTO>> getAllGrades() {
        return new ResponseEntity<>(gradeService.getAllGrades().stream().map(grade -> modelMapper.map(grade, GradeDTO.class)).toList(), HttpStatus.OK);
    }
}
