package org.pollub.campusmate.exception;

public class PasswordDontMatch extends RuntimeException {
    public PasswordDontMatch(String message) {
        super(message);
    }
}
