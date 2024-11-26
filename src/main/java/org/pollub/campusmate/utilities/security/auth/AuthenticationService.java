package org.pollub.campusmate.utilities.security.auth;

import lombok.RequiredArgsConstructor;
import java.security.SecureRandom;
import org.pollub.campusmate.user.dto.UserCreationDto;
import org.pollub.campusmate.user.entity.User;
import org.pollub.campusmate.user.repository.UserRepository;
import org.springframework.stereotype.Service;


import java.util.Random;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;

    public AuthenticationResponse register(UserCreationDto request) {
        User createdUser = User.builder()
                .email(request.getEmail())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())

                .build();



    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
    }
}
