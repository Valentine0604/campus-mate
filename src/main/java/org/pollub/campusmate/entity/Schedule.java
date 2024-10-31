package org.pollub.campusmate.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "schedule")
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class Schedule {

    @Id
    @Column(name = "schedule_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scheduleId;

    @NonNull
    @Column(name = "subject_name", length = 100)
    @Size(message = "Name cannot be longer than 100 characters", max = 100)
    private String subjectName;

    //TODO: date validation

    @NonNull
    @Column(name = "start_date")
    @FutureOrPresent(message = "Date cannot be in the past")
    private LocalDateTime startTime;

    @NonNull
    @Column(name = "end_date")
    @FutureOrPresent(message = "Date cannot be in the past")
    private LocalDateTime endTime;

    @NonNull
    @Column(name = "description", length = 500)
    @Size(message = "Description cannot be longer than 500 characters", max = 500)
    private String description;

    @NonNull
    @Column(name = "location", length = 50)
    @Size(message = "Location cannot be longer than 50 characters", max = 50)
    private String location;

    @NonNull
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;
}