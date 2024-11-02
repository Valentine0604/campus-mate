package org.pollub.campusmate.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.pollub.campusmate.entity.AddressBook;
import org.pollub.campusmate.entity.User;

@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class AddressBookEntryCreationDTO {

    private final String contactName;
    private final AddressBook addressBook;
    private final User user;
}