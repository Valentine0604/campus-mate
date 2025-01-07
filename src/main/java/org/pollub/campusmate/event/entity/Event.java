package org.pollub.campusmate.event.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.pollub.campusmate.team.entity.Team;
import org.pollub.campusmate.user.entity.User;
import org.pollub.campusmate.utilities.validator.ValidDate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

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

    @ManyToMany
    @JoinTable(name = "event_user", joinColumns = @JoinColumn(name = "event_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> users;

    public void addUser(User user) {
        if (users == null) {
            users = new ArrayList<>();
        }
        if (!users.contains(user)) {
            users.add(user);
            user.getEvents().add(this);
        }
    }

    public void removeUser(User user) {
        if (users != null) {
            users.remove(user);
            user.getEvents().remove(this);
        }
    }
}