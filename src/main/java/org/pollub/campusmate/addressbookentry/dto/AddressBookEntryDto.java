package org.pollub.campusmate.addressbookentry.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Setter
public class AddressBookEntryDto {
    private Long entryId;
    private String contactName;
    private String email;
    private String phoneNumber;
    private String classNumber;
    private String notes;
    private Long userId;
}
