package org.pollub.campusmate.addressbook.exception;

public class AddressBookNotFound extends RuntimeException {
  public AddressBookNotFound(String message) {
    super(message);
  }
}
