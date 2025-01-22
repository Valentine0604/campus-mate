package org.pollub.campusmate.schedule.web;

import jakarta.validation.constraints.FutureOrPresent;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.component.VEvent;
import org.pollub.campusmate.schedule.entity.Schedule;
import org.pollub.campusmate.schedule.repository.ScheduleRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@RestController
@AllArgsConstructor
@RequestMapping("/api/schedule")
public class ScheduleController {

    public final ScheduleRepository scheduleRepository;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadSchedule(
            @RequestParam("file") MultipartFile file,
            @RequestParam("groupName") String groupName
    ) {
        try {
            InputStream inputStream = file.getInputStream();
            CalendarBuilder builder = new CalendarBuilder();
            Calendar calendar = builder.build(inputStream);

            for (Object component : calendar.getComponents(Component.VEVENT)) {
                VEvent event = (VEvent) component;

                Schedule schedule = new Schedule();
                schedule.setSubjectName(event.getSummary().getValue());
                schedule.setStartTime(convertToLocalDateTime(event.getStartDate().getDate()));
                schedule.setEndTime(convertToLocalDateTime(event.getEndDate().getDate()));
                schedule.setGroup(groupName);  // Przypisz grupę do harmonogramu

                scheduleRepository.save(schedule);
            }

            return ResponseEntity.ok("Schedule uploaded successfully");

        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(500).body("Error uploading schedule");
        }
    }

    @GetMapping
    public ResponseEntity<Iterable<Schedule>> getSchedule() {
        Iterable<Schedule> schedules = scheduleRepository.findAll();

        // Formatowanie dat do formatu ISO 8601
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        schedules.forEach(schedule -> {
            schedule.setStartTime(LocalDateTime.parse(schedule.getStartTime().format(formatter)));
            schedule.setEndTime(LocalDateTime.parse(schedule.getEndTime().format(formatter)));
        });

        return ResponseEntity.ok(schedules);
    }

    private LocalDateTime convertToLocalDateTime(Date date) {
        return date.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime();
    }
}
