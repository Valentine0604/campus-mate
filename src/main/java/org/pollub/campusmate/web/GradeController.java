package org.pollub.campusmate.web;

import lombok.AllArgsConstructor;
import org.pollub.campusmate.entity.Grade;
import org.pollub.campusmate.service.GradeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Grade> createGrade(@RequestBody Grade grade) {
        return new ResponseEntity<>(gradeService.addGrade(grade), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{gradeId}")
    public ResponseEntity<HttpStatus> deleteGrade(@PathVariable Long gradeId) {
        gradeService.deleteGrade(gradeId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Grade>> getAllGrades() {
        return new ResponseEntity<>(gradeService.getAllGrades(), HttpStatus.OK);
    }
}
