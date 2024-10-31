package org.pollub.campusmate.service;

import lombok.AllArgsConstructor;
import org.pollub.campusmate.entity.User;
import org.pollub.campusmate.exception.UserNotFound;
import org.pollub.campusmate.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFound("User with id " + userId + " not found"));
    }


    public User addUser(User user){
        return userRepository.save(user);
    }

    public void deleteUser(Long userId){
        if(!userRepository.existsById(userId)){
            throw new UserNotFound("Cannot execute delete operation. User with id " + userId + " not found");
        }
        userRepository.deleteById(userId);
    }

    //TODO: update user method @Transactional

    public List<User> getAllUsers(){
        List<User> foundUsers = (List<User>) userRepository.findAll();
        if (foundUsers.isEmpty()) {
            throw new UserNotFound("Users not found");
        }
        return foundUsers;
    }






}