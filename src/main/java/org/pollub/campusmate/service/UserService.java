package org.pollub.campusmate.service;

import lombok.AllArgsConstructor;
import org.pollub.campusmate.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
}