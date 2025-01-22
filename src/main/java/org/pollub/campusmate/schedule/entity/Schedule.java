package org.pollub.campusmate.schedule.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.pollub.campusmate.user.entity.User;
import org.pollub.campusmate.utilities.validator.ValidDate;

import java.time.LocalDateTime;

//@ValidDate
@Entity
@Table(name = "schedule")
@Data
@NoArgsConstructor
public class Schedule {

    @Id
    @Column(name = "schedule_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scheduleId;

    @Column(name = "subject_name")
    private String subjectName;

    @Column(name = "start_date")
    private LocalDateTime startTime;

    @Column(name = "end_date")
    private LocalDateTime endTime;

    @Column(name = "groups")
    private String group;
}