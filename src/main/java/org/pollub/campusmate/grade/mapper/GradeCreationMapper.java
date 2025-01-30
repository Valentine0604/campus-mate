package org.pollub.campusmate.grade.mapper;

import org.pollub.campusmate.grade.dto.GradeCreationDto;
import org.pollub.campusmate.grade.entity.Grade;
import org.springframework.stereotype.Component;

@Component
public class GradeCreationMapper {

    public GradeCreationDto toDto(Grade grade) {
        if(grade == null) return null;

        return new GradeCreationDto(
                grade.getSubjectName(),
                grade.getGrade(),
                grade.getComment(),
                grade.getUser().getUserId(),
                grade.getCreatorId()
        );
    }

    public Grade toEntity(GradeCreationDto dto) {
        if(dto == null) return null;

        Grade grade = new Grade();

        grade.setGrade(dto.getGrade());
        grade.setComment(dto.getComment());
        grade.setSubjectName(dto.getSubjectName());
        grade.setCreatorId(dto.getCreatorId());

        return grade;
    }

}
