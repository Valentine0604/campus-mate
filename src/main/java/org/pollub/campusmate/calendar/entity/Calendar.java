package org.pollub.campusmate.calendar.entity;

import jakarta.persistence.*;
import lombok.*;
import org.pollub.campusmate.event.entity.Event;
import org.pollub.campusmate.user.entity.User;

import java.util.List;

@Entity
@Table(name = "calendar")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    private User user;

    public Calendar(@NonNull String calendarName, List<Event> events, User user) {
        this.calendarName = calendarName;
        this.events = events;
        this.user = user;
    }
}