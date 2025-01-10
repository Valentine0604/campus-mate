package org.pollub.campusmate.team.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.pollub.campusmate.event.entity.Event;
import org.pollub.campusmate.post.entity.Post;
import org.pollub.campusmate.user.entity.User;

import java.util.List;

@Entity
@Table(name = "team")
@Data
@NoArgsConstructor
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

    @Column(name = "creator_id")
    private Long creatorId;

    @ManyToMany
    @JoinTable(name = "team_post", joinColumns = @JoinColumn(name = "team_id"), inverseJoinColumns = @JoinColumn(name = "post_id"))
    private List<Post> posts;

    @ManyToMany
    @JoinTable(name = "team_user", joinColumns = @JoinColumn(name = "team_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> users;

    @OneToMany
    @JoinColumn(name = "team_id", referencedColumnName = "team_id")
    private List<Event> events;
}