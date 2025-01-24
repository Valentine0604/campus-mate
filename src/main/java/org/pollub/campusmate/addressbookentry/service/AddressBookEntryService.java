package org.pollub.campusmate.addressbookentry.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import org.pollub.campusmate.addressbookentry.dto.AddressBookEntryDto;
import org.pollub.campusmate.addressbookentry.entity.AddressBookEntry;
import org.pollub.campusmate.addressbookentry.exception.AddressBookEntryNotFound;
import org.pollub.campusmate.addressbookentry.repository.AddressBookEntryRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
@Builder
public class AddressBookEntryService {

    private AddressBookEntryRepository addressBookEntryRepository;

    public AddressBookEntry getAddressBookEntryById(long addressBookEntryId) {
        return addressBookEntryRepository.findById(addressBookEntryId)
                .orElseThrow(() -> new AddressBookEntryNotFound("Entry with id " + addressBookEntryId + " not found"));
    }

    public List<AddressBookEntry> searchAddressBookEntriesByContactName(String contactName) {
        return addressBookEntryRepository.findByContactNameContainingIgnoreCase(contactName)
                .orElse(Collections.emptyList());
    }

    public void saveAddressBookEntry(AddressBookEntry addressBookEntry) {
        addressBookEntryRepository.save(addressBookEntry);
    }

    public void deleteAddressBookEntry(long addressBookEntryId) {
        if (!addressBookEntryRepository.existsById(addressBookEntryId)) {
            throw new AddressBookEntryNotFound("Cannot execute delete operation. Entry with id " + addressBookEntryId + " not found");
        }
        addressBookEntryRepository.deleteById(addressBookEntryId);
    }
    public void updateAddressBookEntry(long addressBookEntryId, AddressBookEntryDto addressBookEntry) {
        if (!addressBookEntryRepository.existsById(addressBookEntryId)) {
            throw new AddressBookEntryNotFound("Cannot execute update operation. Entry with id " + addressBookEntryId + " not found");
        }
        AddressBookEntry foundAddressBookEntry = getAddressBookEntryById(addressBookEntryId);
        foundAddressBookEntry.setContactName(addressBookEntry.getContactName());
        foundAddressBookEntry.setPhoneNumber(addressBookEntry.getPhoneNumber());
        foundAddressBookEntry.setEmail(addressBookEntry.getEmail());
        foundAddressBookEntry.setClassNumber(addressBookEntry.getClassNumber());
        foundAddressBookEntry.setNotes(addressBookEntry.getNotes());
        addressBookEntryRepository.save(foundAddressBookEntry);
    }

    public List<AddressBookEntry> getAllAddressBookEntries() {
        List<AddressBookEntry> foundEntries = (List<AddressBookEntry>)addressBookEntryRepository.findAll();

        if(foundEntries.isEmpty()){
            throw new AddressBookEntryNotFound("No address book entries found");
        }

        return foundEntries;
    }
}