package org.pollub.campusmate.user.web;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.pollub.campusmate.addressbookentry.entity.AddressBookEntry;
import org.pollub.campusmate.utilities.security.Role;
import org.pollub.campusmate.calendar.dto.CalendarDto;
import org.pollub.campusmate.calendar.entity.Calendar;
import org.pollub.campusmate.event.dto.EventDto;
import org.pollub.campusmate.grade.entity.Grade;
import org.pollub.campusmate.grade.dto.GradeDto;
import org.pollub.campusmate.user.dto.ChangePasswordDto;
import org.pollub.campusmate.user.entity.User;
import org.pollub.campusmate.addressbookentry.service.AddressBookEntryService;
import org.pollub.campusmate.addressbook.service.AddressBookService;
import org.pollub.campusmate.user.service.UserService;
import org.pollub.campusmate.user.dto.UserCreationDto;
import org.pollub.campusmate.user.dto.UserDto;
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
    public ResponseEntity<UserDto> getUser(@PathVariable Long userId) {
        User user = userService.getUser(userId);
        UserDto userDTO = modelMapper.map(user, UserDto.class);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @GetMapping("/role/{role}")
    public ResponseEntity<List<UserDto>> getUsersByRole(@PathVariable Role role) {
        List<User> users = userService.getUsersByRole(role);
        return new ResponseEntity<>(users.stream().map(user -> modelMapper.map(user, UserDto.class)).toList(), HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<String> createUser(@Valid @RequestBody UserCreationDto userDTO, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            bindingResult.getAllErrors().forEach(error -> errorMessage.append(error.getDefaultMessage()).append("\n"));
            return new ResponseEntity<>(errorMessage.toString(),HttpStatus.BAD_REQUEST);
        }
        User user = modelMapper.map(userDTO, User.class);
        user.setCalendar(new Calendar(user.getFirstName() + " " + user.getLastName() + "'s calendar", new ArrayList<>(),user));

        if(user.getRole().equals(Role.ROLE_LECTURER)){
            String contactName = user.getFirstName() + " " + user.getLastName();
            AddressBookEntry entry = new AddressBookEntry(contactName, user.getEmail());
            addressBookEntryService.saveAddressBookEntry(entry);
        }

        userService.addUser(user);
        return new ResponseEntity<>("User created successfully",HttpStatus.CREATED);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return new ResponseEntity<>("User deleted successfully",HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<String> updateUser(@PathVariable Long userId, @RequestBody UserDto userDTO) {
        userService.updateUser(userId, userDTO);
        return new ResponseEntity<>("User updated successfully",HttpStatus.OK);
    }

    @PutMapping("/change-password/{userId}")
    public ResponseEntity<String> changePassword(@PathVariable Long userId, @Valid @RequestBody ChangePasswordDto passwordDTO, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            bindingResult.getAllErrors().forEach(error -> errorMessage.append(error.getDefaultMessage()).append("\n"));
            return new ResponseEntity<>(errorMessage.toString(),HttpStatus.BAD_REQUEST);
        }
        userService.changePassword(userId, passwordDTO);
        return new ResponseEntity<>("Password changed successfully",HttpStatus.OK);
    }

    @GetMapping("/grades/{userId}")
    public ResponseEntity<List<GradeDto>> getGradesByUserId(@PathVariable Long userId) {
        List<Grade> grades = userService.getUser(userId).getGrades();
        List<GradeDto> gradeDTOs = grades.stream().map(grade -> modelMapper.map(grade, GradeDto.class)).toList();
        return new ResponseEntity<>(gradeDTOs, HttpStatus.OK);
    }

    @GetMapping("/calendar/{userId}")
    public ResponseEntity<CalendarDto> getCalendarsByUserId(@PathVariable Long userId) {
        Calendar calendar = userService.getUser(userId).getCalendar();
        CalendarDto calendarDTO = modelMapper.map(calendar, CalendarDto.class);
        return new ResponseEntity<>(calendarDTO, HttpStatus.OK);
    }

    @GetMapping("/events/{userId}")
    public ResponseEntity<List<EventDto>> getEventsByUserId(@PathVariable Long userId) {
        return new ResponseEntity<>(userService.findEventsForUser(userId).stream().map(event -> modelMapper.map(event, EventDto.class)).toList(), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers(){
        return new ResponseEntity<>(userService.getAllUsers().stream().map(user -> modelMapper.map(user, UserDto.class)).toList(), HttpStatus.OK);
    }
}
