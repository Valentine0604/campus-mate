package org.pollub.campusmate.repository;

import org.pollub.campusmate.entity.Calendar;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CalendarRepository extends CrudRepository<Calendar, Long> {

    Calendar findByCalendarId(Long calendarId);

    Calendar findByCalendarName(String calendarName);

    boolean existsByCalendarId(Long calendarId);

    boolean existsByCalendarName(String calendarName);

    void deleteByCalendarId(Long calendarId);

    void deleteByCalendarName(String calendarName);
}
