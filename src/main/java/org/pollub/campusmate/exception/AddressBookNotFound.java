package org.pollub.campusmate.exception;

public class AddressBookNotFound extends RuntimeException {
  public AddressBookNotFound(String message) {
    super(message);
  }
}
