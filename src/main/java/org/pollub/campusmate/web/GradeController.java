package org.pollub.campusmate.web;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.pollub.campusmate.entity.Grade;
import org.pollub.campusmate.service.GradeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/grades")
public class GradeController {

    GradeService gradeService;

    @GetMapping("/{gradeId}")
    public ResponseEntity<Grade> getGrade(@PathVariable Long gradeId) {
        return new ResponseEntity<>(gradeService.getGrade(gradeId), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<String> createGrade(@Valid @RequestBody Grade grade, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            bindingResult.getAllErrors().forEach(error -> errorMessage.append(error.getDefaultMessage()).append("\n"));
            return new ResponseEntity<>(errorMessage.toString(), HttpStatus.BAD_REQUEST);
        }

        gradeService.addGrade(grade);
        return new ResponseEntity<>("Grade added successfully",HttpStatus.CREATED);
    }

    @DeleteMapping("/{gradeId}")
    public ResponseEntity<String> deleteGrade(@PathVariable Long gradeId) {
        gradeService.deleteGrade(gradeId);
        return new ResponseEntity<>("Grade deleted successfully",HttpStatus.NO_CONTENT);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Grade>> getAllGrades() {
        return new ResponseEntity<>(gradeService.getAllGrades(), HttpStatus.OK);
    }
}
