package org.pollub.campusmate.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "calendar")
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

    @OneToMany(mappedBy = "calendar", cascade = CascadeType.ALL)
    private List<Event> events;

    @NonNull
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

}