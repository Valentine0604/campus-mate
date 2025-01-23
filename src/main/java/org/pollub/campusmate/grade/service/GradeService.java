package org.pollub.campusmate.grade.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.pollub.campusmate.grade.dto.GradeDto;
import org.pollub.campusmate.grade.entity.Grade;
import org.pollub.campusmate.grade.exception.GradeNotFound;
import org.pollub.campusmate.grade.repository.GradeRepository;
import org.pollub.campusmate.user.entity.User;
import org.pollub.campusmate.user.exception.UserNotFound;
import org.pollub.campusmate.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class GradeService {

    private final GradeRepository gradeRepository;
    private final UserRepository userRepository;

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

    @Transactional
    public void deleteGrade(Long gradeId) {
        Grade grade = gradeRepository.findById(gradeId)
                .orElseThrow(() -> new GradeNotFound("Cannot execute delete operation. Grade with id " + gradeId + " not found"));
        try {
            User user = grade.getUser();
            user.getGrades().remove(grade);
            userRepository.save(user);
            gradeRepository.delete(grade);
            gradeRepository.flush();
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete grade with id " + gradeId, e);
        }
    }

    public void updateGrade(Long gradeId, GradeDto grade) {
        if(!gradeRepository.existsById(grade.getGradeId())) {
            throw new GradeNotFound("Cannot execute update operation. Grade with id " + grade.getGradeId() + " not found");
        }
        Grade foundGrade = getGrade(gradeId);
        if (grade.getSubjectName() != null) foundGrade.setSubjectName(grade.getSubjectName());
        if (grade.getGrade() != null) foundGrade.setGrade(grade.getGrade());
        if (grade.getComment() != null) foundGrade.setComment(grade.getComment());
        if (grade.getDateOfReceipt() != null) foundGrade.setDateOfReceipt(grade.getDateOfReceipt());
        gradeRepository.save(foundGrade);
    }

    public List<Grade> getAllGrades() {
        List<Grade> foundGrades = (List<Grade>)gradeRepository.findAll();

        if(foundGrades.isEmpty()){
            throw new GradeNotFound("Grades not found");
        }

        return foundGrades;
    }


    public Collection<Grade> getGradesByUserId(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new GradeNotFound("User with id " + userId + " not found");
        }
        return gradeRepository.findByUserUserId(userId);
    }

    public Collection<Grade> getGradesByCreatorId(Long creatorId) {
        if (!userRepository.existsById(creatorId)) {
            throw new UserNotFound("User with id " + creatorId + " not found");
        }
        return gradeRepository.findAllByCreatorId(creatorId);
    }
}