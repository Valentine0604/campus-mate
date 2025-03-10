package org.pollub.campusmate.addressbookentry.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.pollub.campusmate.user.entity.User;

@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class AddressBookEntryCreationDto {

    private final String contactName;
    private final User user;
}
