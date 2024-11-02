package org.pollub.campusmate.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.pollub.campusmate.Role;
import org.pollub.campusmate.validator.ValidPassword;

import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class User{

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @NonNull
    @Column(name = "email", unique = true)
    @Email(message = "Email must be a valid email address")
    @Size(message = "Email cannot be longer than 100 characters ", max = 100)
    private String email;

    @NonNull
    @Column(name = "first_name", length = 50)
    private String firstName;

    @NonNull
    @Column(name = "last_name", length = 50)
    private String lastName;

    @ValidPassword
    @NonNull
    @Column(name = "password", length = 12)
    @Size(message = "Password must contain between 6 and 12 characters", min = 6, max = 12)
    private String password;


    @Enumerated(EnumType.STRING)
    @Column(name = "role_name")
    private Role role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Grade> grades;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Schedule schedule;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Post> posts;

    @ManyToMany
    @JoinTable(name = "team_user", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "team_id"))
    private List<Team> teams;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Calendar calendar;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private AddressBookEntry addressBookEntry;

    @OneToOne
    @JoinColumn(name = "event_id", referencedColumnName = "event_id")
    private Event event;

}