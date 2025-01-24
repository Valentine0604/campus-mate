package org.pollub.campusmate.user.repository;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.NonNull;
import org.pollub.campusmate.user.entity.User;
import org.pollub.campusmate.utilities.security.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByRole(Role role);

    Optional<User> findByEmail(String email);
    Optional<User> findByFirstNameAndLastName(String firstName, String lastName);

    boolean existsByEmail(@NonNull @Email(message = "Email must be a valid email address") @Size(message = "Email cannot be longer than 100 characters ", max = 100) String email);
}

