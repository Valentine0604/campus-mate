package org.pollub.campusmate.service;

import lombok.AllArgsConstructor;
import org.pollub.campusmate.entity.AddressBookEntry;
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
        return addressBookEntryRepository.findById(addressBookEntryId).orElse(null);
    }

    public void saveAddressBookEntry(AddressBookEntry addressBookEntry) {
        addressBookEntryRepository.save(addressBookEntry);
    }

    public void deleteAddressBookEntry(long addressBookEntryId) {
//        if (!addressBookEntryRepository.existsById(addressBookEntryId)) {
//
//        }
        addressBookEntryRepository.deleteById(addressBookEntryId);
    }

    public List<AddressBookEntry> getAllAddressBookEntries() {
        Iterable<AddressBookEntry> entries = addressBookEntryRepository.findAll();
        List<AddressBookEntry> foundEntries = new ArrayList<>();

        entries.forEach(foundEntries::add);

        if(foundEntries.isEmpty()){
            return null;
        }

        return foundEntries;
    }

    //TODO: update entry method
}