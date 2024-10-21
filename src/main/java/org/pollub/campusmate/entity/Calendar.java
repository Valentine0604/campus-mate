package org.pollub.campusmate.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "address_book")
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class Calendar {

    @Id
    @Column(name = "calendar_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long calendarId;

    @NonNull
    @Column(name = "calendar_name")
    private String calendarName;

}