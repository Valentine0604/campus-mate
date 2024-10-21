package org.pollub.campusmate.service;

import lombok.AllArgsConstructor;
import org.pollub.campusmate.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
}