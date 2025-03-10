package org.pollub.campusmate.user.mapper;

import org.pollub.campusmate.user.dto.UserDto;
import org.pollub.campusmate.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDto toDto(User user) {
        if(user == null) return null;


        return new UserDto(
                user.getUserId(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getRole(),
                user.getGroup()
        );
    }

    public User toEntity(UserDto userDto) {
        if(userDto == null) return null;


        User user = new User();
        user.setUserId(userDto.getUserId());
        user.setEmail(userDto.getEmail());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setRole(userDto.getRole());
        user.setGroup(userDto.getGroup());

        return user;
    }
}
