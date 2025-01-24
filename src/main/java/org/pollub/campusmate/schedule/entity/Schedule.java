package org.pollub.campusmate.schedule.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


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