package org.pollub.campusmate.web;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.pollub.campusmate.Role;
import org.pollub.campusmate.dto.*;
import org.pollub.campusmate.entity.Calendar;
import org.pollub.campusmate.entity.Grade;
import org.pollub.campusmate.entity.User;
import org.pollub.campusmate.service.AddressBookEntryService;
import org.pollub.campusmate.service.AddressBookService;
import org.pollub.campusmate.service.UserService;
import org.pollub.campusmate.validator.ValidPassword;
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
    private final AddressBookService addressBookService;
    private final ModelMapper modelMapper;

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long userId) {
        User user = userService.getUser(userId);
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @GetMapping("/role/{role}")
    public ResponseEntity<List<UserDTO>> getUsersByRole(@PathVariable Role role) {
        List<User> users = userService.getUsersByRole(role);
        return new ResponseEntity<>(users.stream().map(user -> modelMapper.map(user, UserDTO.class)).toList(), HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<String> createUser(@Valid @RequestBody UserCreationDTO userDTO, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            bindingResult.getAllErrors().forEach(error -> errorMessage.append(error.getDefaultMessage()).append("\n"));
            return new ResponseEntity<>(errorMessage.toString(),HttpStatus.BAD_REQUEST);
        }
        User user = modelMapper.map(userDTO, User.class);
        user.setCalendar(new Calendar(user.getFirstName() + " " + user.getLastName() + "'s calendar", new ArrayList<>(),user));
        userService.addUser(user);
        return new ResponseEntity<>("User created successfully",HttpStatus.CREATED);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return new ResponseEntity<>("User deleted successfully",HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<String> updateUser(@PathVariable Long userId, @RequestBody UserDTO userDTO) {
        userService.updateUser(userId, userDTO);
        return new ResponseEntity<>("User updated successfully",HttpStatus.OK);
    }

    @PutMapping("change-password/{userId}")
    public ResponseEntity<String> changePassword(@PathVariable Long userId, @Valid @RequestBody ChangePasswordDTO passwordDTO, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            bindingResult.getAllErrors().forEach(error -> errorMessage.append(error.getDefaultMessage()).append("\n"));
            return new ResponseEntity<>(errorMessage.toString(),HttpStatus.BAD_REQUEST);
        }
        userService.changePassword(userId, passwordDTO);
        return new ResponseEntity<>("Password changed successfully",HttpStatus.OK);
    }

    @GetMapping("grades/{userId}")
    public ResponseEntity<List<GradeDTO>> getGradesByUserId(@PathVariable Long userId) {
        List<Grade> grades = userService.getUser(userId).getGrades();
        List<GradeDTO> gradeDTOs = grades.stream().map(grade -> modelMapper.map(grade, GradeDTO.class)).toList();
        return new ResponseEntity<>(gradeDTOs, HttpStatus.OK);
    }

    @GetMapping("calendar/{userId}")
    public ResponseEntity<CalendarDTO> getCalendarsByUserId(@PathVariable Long userId) {
        Calendar calendar = userService.getUser(userId).getCalendar();
        CalendarDTO calendarDTO = modelMapper.map(calendar, CalendarDTO.class);
        return new ResponseEntity<>(calendarDTO, HttpStatus.OK);
    }

    @GetMapping("events/{userId}")
    public ResponseEntity<List<EventDTO>> getEventsByUserId(@PathVariable Long userId) {
        return new ResponseEntity<>(userService.findEventsForUser(userId).stream().map(event -> modelMapper.map(event, EventDTO.class)).toList(), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers(){
        return new ResponseEntity<>(userService.getAllUsers().stream().map(user -> modelMapper.map(user, UserDTO.class)).toList(), HttpStatus.OK);
    }
}
