package org.pollub.campusmate.service;

import lombok.AllArgsConstructor;
import org.pollub.campusmate.repository.TeamRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;

}
