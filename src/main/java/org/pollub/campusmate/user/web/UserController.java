package org.pollub.campusmate.user.web;

import lombok.AllArgsConstructor;
import org.pollub.campusmate.post.dto.PostDto;
import org.pollub.campusmate.team.dto.TeamDto;
import org.pollub.campusmate.utilities.security.Role;
import org.pollub.campusmate.event.dto.EventDto;
import org.pollub.campusmate.event.entity.Event;
import org.pollub.campusmate.grade.entity.Grade;
import org.pollub.campusmate.grade.dto.GradeDto;
import org.pollub.campusmate.user.entity.User;
import org.pollub.campusmate.user.service.UserService;
import org.pollub.campusmate.user.dto.UserDto;
import org.pollub.campusmate.user.mapper.UserMapper;
import org.pollub.campusmate.event.mapper.EventMapper;
import org.pollub.campusmate.grade.mapper.GradeMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;
    private final EventMapper eventMapper;
    private final GradeMapper gradeMapper;

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long userId) {
        User user = userService.getUser(userId);
        UserDto userDTO = userMapper.toDto(user);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @GetMapping("/role/{role}")
    public ResponseEntity<List<UserDto>> getUsersByRole(@PathVariable Role role) {
        List<User> users = userService.getUsersByRole(role);
        List<UserDto> userDTOs = users.stream()
                .map(userMapper::toDto)
                .toList();
        return new ResponseEntity<>(userDTOs, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return new ResponseEntity<>("User deleted successfully", HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<String> updateUser(@PathVariable Long userId, @RequestBody UserDto userDTO) {
        userService.updateUser(userId, userDTO);
        return new ResponseEntity<>("User updated successfully", HttpStatus.OK);
    }

    @GetMapping("/events/{userId}")
    public ResponseEntity<List<EventDto>> getEventsByUserId(@PathVariable Long userId) {
        List<Event> events = userService.findEventsForUser(userId);
        List<EventDto> eventDTOs = events.stream()
                .map(eventMapper::toDto)
                .toList();
        return new ResponseEntity<>(eventDTOs, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> userDTOs = userService.getAllUsers()
                .stream()
                .map(userMapper::toDto)
                .toList();
        return new ResponseEntity<>(userDTOs, HttpStatus.OK);
    }

    @GetMapping("/{userId}/teams")
    public ResponseEntity<List<TeamDto>> getUserTeams(@PathVariable Long userId) {
        List<TeamDto> teamsDto = userService.getUserTeams(userId);
        return new ResponseEntity<>(teamsDto, HttpStatus.OK);
    }

    @GetMapping("/{userId}/posts")
    public ResponseEntity<List<PostDto>> getUserPosts(@PathVariable Long userId) {
        List<PostDto> postsDto = userService.getUserPosts(userId);
        return new ResponseEntity<>(postsDto, HttpStatus.OK);
    }

}
