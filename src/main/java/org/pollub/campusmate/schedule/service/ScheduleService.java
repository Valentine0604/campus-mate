package org.pollub.campusmate.schedule.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.component.VEvent;
import org.pollub.campusmate.schedule.entity.Schedule;
import org.pollub.campusmate.schedule.repository.ScheduleRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Service
@AllArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public void uploadSchedule(MultipartFile file, String groupName) throws Exception {
        InputStream inputStream = file.getInputStream();
        CalendarBuilder builder = new CalendarBuilder();
        Calendar calendar = builder.build(inputStream);

        for (Object component : calendar.getComponents(Component.VEVENT)) {
            VEvent event = (VEvent) component;

            Schedule schedule = new Schedule();
            schedule.setSubjectName(event.getSummary().getValue());
            schedule.setStartTime(convertToLocalDateTime(event.getStartDate().getDate()));
            schedule.setEndTime(convertToLocalDateTime(event.getEndDate().getDate()));
            schedule.setGroup(groupName);

            scheduleRepository.save(schedule);
        }
    }

    public Iterable<Schedule> getSchedule () {
        Iterable<Schedule> schedules = scheduleRepository.findAll();

        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        schedules.forEach(schedule -> {
            schedule.setStartTime(LocalDateTime.parse(schedule.getStartTime().format(formatter)));
            schedule.setEndTime(LocalDateTime.parse(schedule.getEndTime().format(formatter)));
        });

        return schedules;
    }

    private LocalDateTime convertToLocalDateTime(Date date) {
        return date.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime();

    }

    @Transactional
    public ResponseEntity<String> deleteScheduleByGroupName(String groupName) {
        if (groupName == null) {
            return ResponseEntity.badRequest().body("Group name is required");
        }
        if (!scheduleRepository.existsByGroup(groupName)) {
            return ResponseEntity.notFound().build();
        }
        scheduleRepository.deleteScheduleByGroup(groupName);
        return ResponseEntity.ok("Schedule deleted successfully");
    }
}
