package org.pollub.campusmate.event.repository;

import org.pollub.campusmate.event.entity.Event;
import org.pollub.campusmate.user.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface EventRepository extends CrudRepository<Event, Long> {


    Collection<? extends Event> findByUser(User user);

}
