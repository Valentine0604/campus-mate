package org.pollub.campusmate.addressbookentry.mapper;

import org.pollub.campusmate.addressbookentry.dto.AddressBookEntryCreationDto;
import org.pollub.campusmate.addressbookentry.entity.AddressBookEntry;
import org.springframework.stereotype.Component;

@Component
public class AddressBookEntryCreationMapper {

    public AddressBookEntryCreationDto toDto(AddressBookEntry addressBookEntry) {
        if (addressBookEntry == null) return null;

        return new AddressBookEntryCreationDto(
                addressBookEntry.getContactName(),
                addressBookEntry.getUser()
        );
    }

    public AddressBookEntry toEntity(AddressBookEntryCreationDto addressBookEntryCreationDto) {
        if (addressBookEntryCreationDto == null) return null;

        AddressBookEntry addressBookEntry = new AddressBookEntry();
        addressBookEntry.setContactName(addressBookEntryCreationDto.getContactName());
        addressBookEntry.setUser(addressBookEntryCreationDto.getUser());

        return addressBookEntry;
    }
}
