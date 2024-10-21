package org.pollub.campusmate.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "address_book")
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
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

    //TODO: date validation

    @NonNull
    @Column(name = "start_date")
    @FutureOrPresent
    private LocalDateTime startDate;

    @NonNull
    @Column(name = "end_date")
    @FutureOrPresent
    private LocalDateTime endDate;
}