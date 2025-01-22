package org.pollub.campusmate.utilities.web;

import org.pollub.campusmate.addressbookentry.exception.AddressBookEntryNotFound;
import org.pollub.campusmate.event.exception.EventNotFound;
import org.pollub.campusmate.grade.exception.GradeNotFound;
import org.pollub.campusmate.post.exception.PostNotFound;
import org.pollub.campusmate.team.exception.TeamNotFound;
import org.pollub.campusmate.user.exception.EmailAlreadyExistsException;
import org.pollub.campusmate.user.exception.PasswordDoesNotMatch;
import org.pollub.campusmate.user.exception.UserNotFound;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFound.class)
    public ResponseEntity<String> handleUserNotFound(UserNotFound ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TeamNotFound.class)
    public ResponseEntity<String> handleTeamNotFound(TeamNotFound ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PostNotFound.class)
    public ResponseEntity<String> handlePostNotFound(PostNotFound ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(GradeNotFound.class)
    public ResponseEntity<String> handleGradeNotFound(GradeNotFound ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EventNotFound.class)
    public ResponseEntity<String> handleEventNotFound(EventNotFound ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AddressBookEntryNotFound.class)
    public ResponseEntity<String> handleAddressBookEntryNotFound(AddressBookEntryNotFound ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<String> handleEmailAlreadyExists(EmailAlreadyExistsException ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(PasswordDoesNotMatch.class)
    public ResponseEntity<String> handlePasswordDontMatch(PasswordDoesNotMatch ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }
}
