package org.pollub.campusmate.schedule.web;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.pollub.campusmate.schedule.entity.Schedule;
import org.pollub.campusmate.schedule.service.ScheduleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@AllArgsConstructor
@RequestMapping("/api/schedule")
public class ScheduleController {

    public final ScheduleService scheduleService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadSchedule(
            @RequestParam("file") MultipartFile file,
            @RequestParam("groupName") String groupName
    ) {
        try {
            scheduleService.uploadSchedule(file, groupName);
            return ResponseEntity.ok("Schedule uploaded successfully");
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(500).body("Error uploading schedule");
        }
    }

    @GetMapping
    public ResponseEntity<Iterable<Schedule>> getSchedule() {
        Iterable<Schedule> schedules = scheduleService.getSchedule();

        return ResponseEntity.ok(schedules);
    }


    @DeleteMapping("/{groupName}")
    public ResponseEntity<String> deleteScheduleByGroupName(@PathVariable String groupName) {
        return scheduleService.deleteScheduleByGroupName(groupName);
    }
}
