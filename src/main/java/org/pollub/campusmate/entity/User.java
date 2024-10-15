package org.pollub.campusmate.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.pollub.campusmate.Role;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class User{

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @NonNull
    @Column(name = "email")
    @Email
    @Size(message = "Email must be a valid email address", max = 100)
    private String email;

    @NonNull
    @Column(name = "first_name")
    private String firstName;

    @NonNull
    @Column(name = "last_name")
    private String lastName;

    //TODO: password validation

    @NonNull
    @Column(name = "password")
    @Size(message = "Password must contain between 6 and 12 characters", min = 6, max = 12)
    private String password;

    @NonNull
    @Enumerated(EnumType.STRING)
    @Column(name = "role_name")
    private Role role;


}
