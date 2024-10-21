package org.pollub.campusmate.service;

import lombok.AllArgsConstructor;
import org.pollub.campusmate.repository.GradeRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GradeService {

    private final GradeRepository gradeRepository;
}