package org.pollub.campusmate.service;

import lombok.AllArgsConstructor;
import org.pollub.campusmate.entity.Grade;
import org.pollub.campusmate.exception.GradeNotFound;
import org.pollub.campusmate.repository.GradeRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class GradeService {

    //TODO: exception handling
    private final GradeRepository gradeRepository;

    public Grade getGrade(long gradeId) {

        return gradeRepository.findById(gradeId)
                .orElseThrow(() -> new GradeNotFound("Grade with id " + gradeId + " not found"));
    }

    public void addGrade(Grade grade) {
        gradeRepository.save(grade);
    }

    public void deleteGrade(long gradeId) {
        if(!gradeRepository.existsById(gradeId)) {
            throw new GradeNotFound("Cannot execute delete operation. Grade with id " + gradeId + " not found");
        }
        gradeRepository.deleteById(gradeId);
    }

    public List<Grade> getAllGrades() {
        Iterable<Grade> grades = gradeRepository.findAll();
        List<Grade> foundGrades = new ArrayList<>();

        grades.forEach(foundGrades::add);

        if(foundGrades.isEmpty()){
            throw new GradeNotFound("Grades not found");
        }

        return foundGrades;
    }
    //TODO: update grade method
}