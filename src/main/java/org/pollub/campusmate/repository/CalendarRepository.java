package org.pollub.campusmate.repository;

import org.pollub.campusmate.entity.Calendar;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CalendarRepository extends CrudRepository<Calendar, Long> {

}
