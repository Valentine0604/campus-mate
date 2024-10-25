package org.pollub.campusmate.service;

import lombok.AllArgsConstructor;
import org.pollub.campusmate.entity.User;
import org.pollub.campusmate.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    //TODO: exception handling

    public User getUser(Long userId){
        return userRepository.findById(userId).orElse(null);
    }

    public User addUser(User user){
        return userRepository.save(user);
    }

    public void deleteUser(Long userId){
//        if(!userRepository.existsById(userId)){
//
//        }
        userRepository.deleteById(userId);
    }

    //TODO: update user method @Transactional

    public List<User> getAllUsers(){
        Iterable<User> users = userRepository.findAll();
        List<User> foundUsers = new ArrayList<>();

        users.forEach(foundUsers::add);

        if(foundUsers.isEmpty()){
            return null;
        }

        return foundUsers;
    }






}