package org.pollub.campusmate.schedule.repository;

import org.pollub.campusmate.schedule.entity.Schedule;
import org.pollub.campusmate.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

}