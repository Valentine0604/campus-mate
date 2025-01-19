package org.pollub.campusmate.addressbookentry.mapper;

import lombok.AllArgsConstructor;
import org.pollub.campusmate.addressbookentry.dto.AddressBookEntryDto;
import org.pollub.campusmate.addressbookentry.entity.AddressBookEntry;
import org.pollub.campusmate.user.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AddressBookEntryMapper {

    public AddressBookEntryDto toDto(AddressBookEntry addressBookEntry) {

        if (addressBookEntry == null) return null;

        return new AddressBookEntryDto(
                addressBookEntry.getEntryId(),
                addressBookEntry.getContactName(),
                addressBookEntry.getEmail(),
                addressBookEntry.getPhoneNumber(),
                addressBookEntry.getClassNumber(),
                addressBookEntry.getNotes(),
                addressBookEntry.getUser().getUserId()
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
