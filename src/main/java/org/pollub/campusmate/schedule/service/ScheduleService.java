package org.pollub.campusmate.schedule.service;

import lombok.AllArgsConstructor;
import org.pollub.campusmate.schedule.entity.Schedule;
import org.pollub.campusmate.schedule.exception.ScheduleNotFound;
import org.pollub.campusmate.schedule.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public Schedule getSchedule(long scheduleId) {

        return scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new ScheduleNotFound("Schedule with id " + scheduleId + " not found"));
    }

    public void addSchedule(Schedule schedule) {
        scheduleRepository.save(schedule);
    }

    public void deleteSchedule(long scheduleId) {
        if(!scheduleRepository.existsById(scheduleId)) {
            throw new ScheduleNotFound("Cannot execute delete operation. Schedule with id " + scheduleId + " not found");
        }
        scheduleRepository.deleteById(scheduleId);
    }

    public List<Schedule> getAllSchedules() {
        List<Schedule> foundSchedules = (List<Schedule>)scheduleRepository.findAll();

        if(foundSchedules.isEmpty()){
            throw new ScheduleNotFound("No schedules found");
        }

        return foundSchedules;
    }

}