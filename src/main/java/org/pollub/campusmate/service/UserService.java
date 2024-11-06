package org.pollub.campusmate.service;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.pollub.campusmate.Role;
import org.pollub.campusmate.dto.ChangePasswordDTO;
import org.pollub.campusmate.dto.EventDTO;
import org.pollub.campusmate.dto.UserDTO;
import org.pollub.campusmate.entity.Event;
import org.pollub.campusmate.entity.Grade;
import org.pollub.campusmate.entity.Team;
import org.pollub.campusmate.entity.User;
import org.pollub.campusmate.exception.EmailAlreadyExistsException;
import org.pollub.campusmate.exception.PasswordDontMatch;
import org.pollub.campusmate.exception.UserNotFound;
import org.pollub.campusmate.repository.EventRepository;
import org.pollub.campusmate.repository.UserRepository;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final EventRepository eventRepository;


    public User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFound("User with id " + userId + " not found"));
    }

    public List<User> getUsersByRole(Role role) {
        List<User> foundUsers = userRepository.findByRole(role);
        if (foundUsers.isEmpty()) {
            throw new UserNotFound("Users not found");
        }
        return foundUsers;
    }


    public void addUser(@Valid User user) throws EmailAlreadyExistsException {
        if(userRepository.existsByEmail(user.getEmail())) {
            throw new EmailAlreadyExistsException("User with email " + user.getEmail() + " already exists");
        }
        userRepository.save(user);
    }

    public void deleteUser(Long userId){
        if(!userRepository.existsById(userId)){
            throw new UserNotFound("Cannot execute delete operation. User with id " + userId + " not found");
        }
        userRepository.deleteById(userId);
    }

    @Transactional
    public void updateUser(Long userId, UserDTO user) {
        if(!userRepository.existsById(userId)){
            throw new UserNotFound("Cannot execute update operation. User with id " + userId + " not found");
        }
        User foundUser = getUser(userId);
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new EmailAlreadyExistsException("User with email " + user.getEmail() + " already exists");
        }
        if(user.getFirstName() != null) {
            foundUser.setFirstName(user.getFirstName());
        }
        if(user.getLastName() != null) {
            foundUser.setLastName(user.getLastName());
        }
        if(user.getEmail() != null) {
            foundUser.setEmail(user.getEmail());
        }
        if(user.getRole() != null) {
            foundUser.setRole(user.getRole());
        }
        userRepository.save(foundUser);
    }

    @Transactional
    public void changePassword(Long userId, ChangePasswordDTO passwordDTO) {
        if(!userRepository.existsById(userId)){
            throw new UserNotFound("Cannot execute update operation. User with id " + userId + " not found");
        }
        if(passwordDTO.getOldPassword() == null || passwordDTO.getNewPassword() == null || passwordDTO.getConfirmPassword() == null) {
            throw new PasswordDontMatch("Password cannot be empty");
        }
        if(!passwordDTO.getOldPassword().equals(getUser(userId).getPassword())) {
            throw new PasswordDontMatch("Old password is incorrect");
        }
        if(passwordDTO.getNewPassword().equals(passwordDTO.getOldPassword())) {
            throw new PasswordDontMatch("New password cannot be the same as old password");
        }
        if(!passwordDTO.getNewPassword().equals(passwordDTO.getConfirmPassword())) {
            throw new PasswordDontMatch("New and confirm passwords don't match");
        }
        String password = passwordDTO.getNewPassword();
        User user = getUser(userId);
        user.setPassword(password);
        userRepository.save(user);
    }

    public List<Event> findEventsForUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFound("User not found"));

        Set<Event> userEvents = new HashSet<>();

        for (Team team : user.getTeams()) {
            userEvents.addAll(team.getEvents());
        }

        userEvents.addAll(eventRepository.findByUser(user));

        return new ArrayList<>(userEvents);
    }

    public List<User> getAllUsers(){
        List<User> foundUsers = (List<User>) userRepository.findAll();
        if (foundUsers.isEmpty()) {
            throw new UserNotFound("Users not found");
        }
        return foundUsers;
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFound("User with email " + email + " not found"));
    }

    public User getUserByFirstNameAndLastName(String firstName, String lastName) {
        return userRepository.findByFirstNameAndLastName(firstName, lastName)
                .orElseThrow(() -> new UserNotFound("User with name " + firstName + " " + lastName + " not found"));
    }

    //TODO: add get password after security
    public List<String> getCurrentPassword() {
        return null;
    }

}