package org.pollub.campusmate.service;

import lombok.AllArgsConstructor;
import org.pollub.campusmate.entity.Calendar;
import org.pollub.campusmate.repository.CalendarRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CalendarService {

    private final CalendarRepository calendarRepository;

    public Calendar getCalendar(long calendarId){
        return calendarRepository.findById(calendarId).orElseThrow(null);
    }

    public Calendar createCalendar(Calendar calendar){
        return calendarRepository.save(calendar);
    }

    public void deleteCalendar(long calendarId){
//        if(!calendarRepository.existsById(calendarId){
//
//        }
        calendarRepository.deleteById(calendarId);
    }

    public List<Calendar> getAllCalendars(){
        Iterable<Calendar> calendars = calendarRepository.findAll();
        List<Calendar> foundCalendars = new ArrayList<>();

        calendars.forEach(foundCalendars::add);

        if(foundCalendars.isEmpty()){
            return null;
        }

        return foundCalendars;
    }

}