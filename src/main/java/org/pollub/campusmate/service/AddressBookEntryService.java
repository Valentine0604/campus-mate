package org.pollub.campusmate.service;

import lombok.AllArgsConstructor;
import org.pollub.campusmate.entity.AddressBookEntry;
import org.pollub.campusmate.exception.AddressBookEntryNotFound;
import org.pollub.campusmate.repository.AddressBookEntryRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class AddressBookEntryService {

    //TODO: exception handling

    private AddressBookEntryRepository addressBookEntryRepository;

    public AddressBookEntry getAddressBookEntry(long addressBookEntryId) {
        return addressBookEntryRepository.findById(addressBookEntryId)
                .orElseThrow(() -> new AddressBookEntryNotFound("Entry with id " + addressBookEntryId + " not found"));
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

    public List<AddressBookEntry> getAllAddressBookEntries() {
        List<AddressBookEntry> foundEntries = (List<AddressBookEntry>)addressBookEntryRepository.findAll();

        if(foundEntries.isEmpty()){
            throw new AddressBookEntryNotFound("No address book entries found");
        }

        return foundEntries;
    }

    //TODO: update entry method
}