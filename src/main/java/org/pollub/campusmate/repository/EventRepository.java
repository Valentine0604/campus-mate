package org.pollub.campusmate.repository;

import org.pollub.campusmate.entity.Event;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends CrudRepository<Event, Long> {

    Event findByEventName(String eventName);

    boolean existsByEventId(Long eventId);

    boolean existsByEventName(String eventName);

    void deleteByEventName(String eventName);
}
