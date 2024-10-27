package org.pollub.campusmate.service;

import lombok.AllArgsConstructor;
import org.pollub.campusmate.entity.Schedule;
import org.pollub.campusmate.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public Schedule getSchedule(long scheduleId) {
        return scheduleRepository.findById(scheduleId).orElse(null);
    }

    public void addSchedule(Schedule schedule) {
        scheduleRepository.save(schedule);
    }

    public void deleteSchedule(long scheduleId) {
//        if(!scheduleRepository.existsById(scheduleId)) {
//
//        }
        scheduleRepository.deleteById(scheduleId);
    }

    public List<Schedule> getAllSchedules() {
        Iterable<Schedule> schedules = scheduleRepository.findAll();
        List<Schedule> foundSchedules = new ArrayList<>();

        schedules.forEach(foundSchedules::add);

        if(foundSchedules.isEmpty()){
            return null;
        }

        return foundSchedules;
    }

}