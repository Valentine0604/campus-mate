package org.pollub.campusmate.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "team")
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class Team {

    @Id
    @Column(name = "team_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long teamId;

    @NonNull
    @Column(name = "team_name", length = 50)
    @Size(message = "Name cannot be longer than 50 characters", max = 50)
    private String teamName;

    @Column(name = "description", length = 200)
    @Size(message = "Description cannot be longer than 200 characters", max = 200)
    private String description;

    @ManyToOne
    @JoinColumn(name = "post id", referencedColumnName = "post_id")
    private Post post;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "user id", referencedColumnName = "user_id")
    private User user;

    @ManyToMany
    @JoinTable(name = "team_event", joinColumns = @JoinColumn(name = "team_id"), inverseJoinColumns = @JoinColumn(name = "event_id"))
    private List<Event> events;
}