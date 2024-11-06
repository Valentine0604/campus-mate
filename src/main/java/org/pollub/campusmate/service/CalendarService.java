package org.pollub.campusmate.service;

import lombok.AllArgsConstructor;
import org.pollub.campusmate.entity.Calendar;
import org.pollub.campusmate.exception.CalendarNotFound;
import org.pollub.campusmate.repository.CalendarRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CalendarService {

    private final CalendarRepository calendarRepository;

    public Calendar getCalendar(long calendarId){

        return calendarRepository.findById(calendarId)
                .orElseThrow(() -> new CalendarNotFound("Calendar with id " + calendarId + " not found"));
    }

    public List<Calendar> getCalendarsByUser(Long userId) {
        return calendarRepository.findByUserUserId(userId);
    }

    public Calendar createCalendar(Calendar calendar){
        return calendarRepository.save(calendar);
    }

    public void deleteCalendar(long calendarId){
        if(!calendarRepository.existsById(calendarId)){
            throw new CalendarNotFound("Cannot execute delete operation. Calendar with id " + calendarId + " not found");
        }
        calendarRepository.deleteById(calendarId);
    }

    public void updateCalendar(Long calendarId, Calendar calendar) {
        if(!calendarRepository.existsById(calendarId)){
            throw new CalendarNotFound("Cannot execute update operation. Calendar with id " + calendarId + " not found");
        }
        Calendar foundCalendar = calendarRepository.findById(calendarId).get();
        calendar.setCalendarId(calendarId);
        calendar.setEvents(new ArrayList<>(foundCalendar.getEvents()));
        calendar.setUser(foundCalendar.getUser());
        calendarRepository.save(calendar);
    }

    public List<Calendar> getAllCalendars(){
        List<Calendar> foundCalendars = (List<Calendar>)calendarRepository.findAll();

        if(foundCalendars.isEmpty()){
            throw new CalendarNotFound("No calendars found");
        }

        return foundCalendars;
    }



}