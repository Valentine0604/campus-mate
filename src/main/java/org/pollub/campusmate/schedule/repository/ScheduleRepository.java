package org.pollub.campusmate.schedule.repository;

import org.pollub.campusmate.schedule.entity.Schedule;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends CrudRepository<Schedule, Long> {

}
