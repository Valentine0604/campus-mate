package org.pollub.campusmate.repository;

import org.pollub.campusmate.entity.Grade;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface GradeRepository extends CrudRepository<Grade, Long> {

    Collection<? extends Grade> findByUserUserId(Long studentId);

    Collection<? extends Grade> findBySubjectName(String subjectName);
}
