package org.pollub.campusmate.repository;

import org.pollub.campusmate.entity.Schedule;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends CrudRepository<Schedule, Long> {

    Schedule findBySubjectName(String subjectName);

    boolean existsByScheduleId(Long scheduleId);

    boolean existsBySubjectName(String subjectName);

    void deleteByScheduleId(Long scheduleId);

    void deleteBySubjectName(String subjectName);
}
