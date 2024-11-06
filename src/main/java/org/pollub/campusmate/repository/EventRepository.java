package org.pollub.campusmate.repository;

import org.pollub.campusmate.entity.Event;
import org.pollub.campusmate.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface EventRepository extends CrudRepository<Event, Long> {


    Collection<? extends Event> findByUser(User user);

}
