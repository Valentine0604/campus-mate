package org.pollub.campusmate.repository;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.NonNull;
import org.pollub.campusmate.Role;
import org.pollub.campusmate.entity.User;
import org.pollub.campusmate.service.UserService;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    List<User> findByRole(Role role);


    boolean existsByEmail(@NonNull @Email(message = "Email must be a valid email address") @Size(message = "Email cannot be longer than 100 characters ", max = 100) String email);
}

