package org.pollub.campusmate.calendar.repository;

import org.pollub.campusmate.calendar.entity.Calendar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CalendarRepository extends JpaRepository<Calendar, Long> {


    List<Calendar> findCalendarByUserUserId(Long userId);
    Calendar findByCalendarName(String name);
}
