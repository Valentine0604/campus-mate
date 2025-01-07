package org.pollub.campusmate.addressbookentry.mapper;

import org.pollub.campusmate.addressbookentry.dto.AddressBookEntryDto;
import org.pollub.campusmate.addressbookentry.entity.AddressBookEntry;
import org.springframework.stereotype.Component;

@Component
public class AddressBookEntryMapper {

    public AddressBookEntryDto toDto(AddressBookEntry addressBookEntry) {

        if (addressBookEntry == null) return null;

        return new AddressBookEntryDto(
                addressBookEntry.getEntryId(),
                addressBookEntry.getContactName(),
                addressBookEntry.getEmail(),
                addressBookEntry.getPhoneNumber(),
                addressBookEntry.getClassNumber(),
                addressBookEntry.getNotes()
        );
    }

    public AddressBookEntry toEntity(AddressBookEntryDto addressBookEntryDto) {

        if (addressBookEntryDto == null) return null;

        AddressBookEntry addressBookEntry = new AddressBookEntry();
        addressBookEntry.setContactName(addressBookEntryDto.getContactName());
        addressBookEntry.setEmail(addressBookEntryDto.getEmail());
        addressBookEntry.setPhoneNumber(addressBookEntryDto.getPhoneNumber());
        addressBookEntry.setClassNumber(addressBookEntryDto.getClassNumber());
        addressBookEntry.setNotes(addressBookEntryDto.getNotes());

        return addressBookEntry;
    }
}
