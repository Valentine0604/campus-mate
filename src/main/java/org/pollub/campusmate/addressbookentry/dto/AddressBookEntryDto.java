package org.pollub.campusmate.addressbookentry.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class AddressBookEntryDto {
    private final String contactName;
    private final String email;
    private final String phoneNumber;
    private final String classNumber;
    private final String notes;
}
