package org.pollub.campusmate.user.repository;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.NonNull;
import org.pollub.campusmate.utilities.security.Role;
import org.pollub.campusmate.user.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    List<User> findByRole(Role role);

    @Query("SELECT u FROM User u WHERE u.email = :email")
    Optional<User> findByEmail(String email);
    Optional<User> findByFirstNameAndLastName(String firstName, String lastName);


    boolean existsByEmail(@NonNull @Email(message = "Email must be a valid email address") @Size(message = "Email cannot be longer than 100 characters ", max = 100) String email);
}

