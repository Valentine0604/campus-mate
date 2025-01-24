package org.pollub.campusmate.event.repository;

import org.pollub.campusmate.event.entity.Event;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends CrudRepository<Event, Long> {



}
