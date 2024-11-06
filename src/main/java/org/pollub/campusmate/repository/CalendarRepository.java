package org.pollub.campusmate.repository;

import org.pollub.campusmate.entity.Calendar;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CalendarRepository extends CrudRepository<Calendar, Long> {

    List<Calendar> findByUserUserId(Long userId);
}
