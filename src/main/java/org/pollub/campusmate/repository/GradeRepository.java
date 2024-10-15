package org.pollub.campusmate.repository;

import org.pollub.campusmate.entity.Grade;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GradeRepository extends CrudRepository<Grade, Long> {

    Grade findByGradeId(Long gradeId);

    boolean existsByGradeId(Long gradeId);

    void deleteByGradeId(Long gradeId);
}
