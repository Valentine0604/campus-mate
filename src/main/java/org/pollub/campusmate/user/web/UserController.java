package org.pollub.campusmate.user.web;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.pollub.campusmate.addressbookentry.entity.AddressBookEntry;
import org.pollub.campusmate.utilities.security.Role;
import org.pollub.campusmate.event.dto.EventDto;
import org.pollub.campusmate.event.entity.Event;
import org.pollub.campusmate.grade.entity.Grade;
import org.pollub.campusmate.grade.dto.GradeDto;
import org.pollub.campusmate.user.dto.ChangePasswordDto;
import org.pollub.campusmate.user.entity.User;
import org.pollub.campusmate.addressbookentry.service.AddressBookEntryService;
import org.pollub.campusmate.user.service.UserService;
import org.pollub.campusmate.user.dto.UserDto;
import org.pollub.campusmate.user.mapper.UserMapper;
import org.pollub.campusmate.event.mapper.EventMapper;
import org.pollub.campusmate.grade.mapper.GradeMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final AddressBookEntryService addressBookEntryService;
    private final UserMapper userMapper;  // Używamy UserMapper zamiast ModelMapper
    private final EventMapper eventMapper;  // Mapper dla Event
    private final GradeMapper gradeMapper;  // Mapper dla Grade

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long userId) {
        User user = userService.getUser(userId);
        UserDto userDTO = userMapper.toDto(user);  // Zmieniamy na użycie userMapper
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @GetMapping("/role/{role}")
    public ResponseEntity<List<UserDto>> getUsersByRole(@PathVariable Role role) {
        List<User> users = userService.getUsersByRole(role);
        List<UserDto> userDTOs = users.stream()
                .map(userMapper::toDto)  // Zmieniamy na użycie userMapper
                .toList();
        return new ResponseEntity<>(userDTOs, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> createUser(@Valid @RequestBody UserDto userDTO, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            bindingResult.getAllErrors().forEach(error -> errorMessage.append(error.getDefaultMessage()).append("\n"));
            return new ResponseEntity<>(errorMessage.toString(), HttpStatus.BAD_REQUEST);
        }
        User user = userMapper.toEntity(userDTO);  // Zmieniamy na użycie userMapper

        if(user.getRole().equals(Role.ROLE_LECTURER)){
            String contactName = user.getFirstName() + " " + user.getLastName();
            AddressBookEntry entry = new AddressBookEntry(contactName, user.getEmail());
            addressBookEntryService.saveAddressBookEntry(entry);
        }

        userService.addUser(user);
        return new ResponseEntity<>("User created successfully", HttpStatus.CREATED);
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

    @PutMapping("/change-password/{userId}")
    public ResponseEntity<String> changePassword(@PathVariable Long userId, @Valid @RequestBody ChangePasswordDto passwordDTO, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            bindingResult.getAllErrors().forEach(error -> errorMessage.append(error.getDefaultMessage()).append("\n"));
            return new ResponseEntity<>(errorMessage.toString(), HttpStatus.BAD_REQUEST);
        }
        userService.changePassword(userId, passwordDTO);
        return new ResponseEntity<>("Password changed successfully", HttpStatus.OK);
    }

    @GetMapping("/grades/{userId}")
    public ResponseEntity<List<GradeDto>> getGradesByUserId(@PathVariable Long userId) {
        List<Grade> grades = userService.getUser(userId).getGrades();
        List<GradeDto> gradeDTOs = grades.stream()
                .map(gradeMapper::toDto)  // Zmieniamy na użycie gradeMapper
                .toList();
        return new ResponseEntity<>(gradeDTOs, HttpStatus.OK);
    }

    @GetMapping("/events/{userId}")
    public ResponseEntity<List<EventDto>> getEventsByUserId(@PathVariable Long userId) {
        List<Event> events = userService.findEventsForUser(userId);
        List<EventDto> eventDTOs = events.stream()
                .map(eventMapper::toDto)  // Zmieniamy na użycie eventMapper
                .toList();
        return new ResponseEntity<>(eventDTOs, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> userDTOs = userService.getAllUsers()
                .stream()
                .map(userMapper::toDto)  // Zmieniamy na użycie userMapper
                .toList();
        return new ResponseEntity<>(userDTOs, HttpStatus.OK);
    }
}
