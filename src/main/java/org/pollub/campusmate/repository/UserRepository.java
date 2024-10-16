package org.pollub.campusmate.repository;

import org.pollub.campusmate.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    User findByEmail(String email);

    boolean existsByUserId(Long userId);

    boolean existsByEmail(String email);

    void deleteByEmail(String email);

}
