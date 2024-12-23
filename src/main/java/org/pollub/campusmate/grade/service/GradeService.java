package org.pollub.campusmate.grade.service;

import lombok.AllArgsConstructor;
import org.pollub.campusmate.grade.entity.Grade;
import org.pollub.campusmate.grade.exception.GradeNotFound;
import org.pollub.campusmate.grade.repository.GradeRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class GradeService {

    private final GradeRepository gradeRepository;

    public Grade getGrade(long gradeId) {

        return gradeRepository.findById(gradeId)
                .orElseThrow(() -> new GradeNotFound("Grade with id " + gradeId + " not found"));
    }

    public List<Grade> getGradesByStudentId(Long studentId) {
        List<Grade> foundGrades = new ArrayList<>(gradeRepository.findByUserUserId(studentId));
        if(foundGrades.isEmpty()){
            throw new GradeNotFound("Grades not found");
        }
        return foundGrades;
    }

    public List<Grade> getGradesBySubjectName(String subjectName) {
        List<Grade> foundGrades = new ArrayList<>(gradeRepository.findBySubjectName(subjectName));
        if(foundGrades.isEmpty()){
            throw new GradeNotFound("Grades not found");
        }
        return foundGrades;
    }

    public Grade addGrade(Grade grade) {
        return gradeRepository.save(grade);
    }

    public void deleteGrade(long gradeId) {
        if(!gradeRepository.existsById(gradeId)) {
            throw new GradeNotFound("Cannot execute delete operation. Grade with id " + gradeId + " not found");
        }
        gradeRepository.deleteById(gradeId);
    }

    public void updateGrade(Long gradeId, Grade grade) {
        if(!gradeRepository.existsById(grade.getGradeId())) {
            throw new GradeNotFound("Cannot execute update operation. Grade with id " + grade.getGradeId() + " not found");
        }
        Grade foundGrade = getGrade(grade.getGradeId());
        foundGrade.setGradeId(grade.getGradeId());
        foundGrade.setUser(grade.getUser());
        gradeRepository.save(grade);
    }

    public List<Grade> getAllGrades() {
        List<Grade> foundGrades = (List<Grade>)gradeRepository.findAll();

        if(foundGrades.isEmpty()){
            throw new GradeNotFound("Grades not found");
        }

        return foundGrades;
    }



}