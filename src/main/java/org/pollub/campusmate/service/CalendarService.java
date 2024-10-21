package org.pollub.campusmate.service;

import lombok.AllArgsConstructor;
import org.pollub.campusmate.repository.CalendarRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CalendarService {

    private final CalendarRepository CalendarRepository;
}