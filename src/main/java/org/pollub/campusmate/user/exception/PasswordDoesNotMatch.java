package org.pollub.campusmate.user.exception;

public class PasswordDoesNotMatch extends RuntimeException {
    public PasswordDoesNotMatch(String message) {
        super(message);
    }
}
