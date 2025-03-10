package org.pollub.campusmate.event.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.pollub.campusmate.team.entity.Team;
import org.pollub.campusmate.utilities.validator.ValidDate;

import java.time.LocalDate;

@ValidDate
@Entity
@Table(name = "event")
@Data
@NoArgsConstructor
public class Event {
    @Id
    @Column(name = "event_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eventId;

    @NonNull
    @Column(name = "event_name", length = 100)
    @Size(message = "Name cannot be longer than 100 characters", max = 100)
    private String eventName;

    @NonNull
    @Column(name = "event_description", length = 200)
    @Size(message = "Description cannot be longer than 100 characters", max = 200)
    private String eventDescription;

    @NonNull
    @Column(name = "start_date")
    @FutureOrPresent
    private LocalDate startDate;

    @NonNull
    @Column(name = "end_date")
    @FutureOrPresent
    private LocalDate endDate;

    @ManyToOne
    @JoinColumn(name = "team_id", referencedColumnName = "team_id", nullable = false)
    private Team team;

}