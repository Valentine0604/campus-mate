package org.pollub.campusmate.grade.mapper;

import org.pollub.campusmate.grade.dto.GradeDto;
import org.pollub.campusmate.grade.entity.Grade;
import org.springframework.stereotype.Component;

@Component
public class GradeMapper {

    public GradeDto toDto(Grade grade){
        if(grade == null) return null;

        return new GradeDto(
                grade.getGradeId(),
                grade.getSubjectName(),
                grade.getGrade(),
                grade.getComment(),
                grade.getDateOfReceipt()
        );
    }

    public Grade toEntity(GradeDto dto){
        if(dto == null) return null;

        Grade grade = new Grade();
        grade.setGradeId(dto.getGradeId());
        grade.setSubjectName(dto.getSubjectName());
        grade.setGrade(dto.getGrade());
        grade.setComment(dto.getComment());
        grade.setDateOfReceipt(dto.getDateOfReceipt());

        return grade;
    }
}
