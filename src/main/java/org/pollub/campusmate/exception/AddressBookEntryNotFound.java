package org.pollub.campusmate.exception;

public class AddressBookEntryNotFound extends RuntimeException {
    public AddressBookEntryNotFound(String message) {
        super(message);
    }
}
