package org.pollub.campusmate.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import org.pollub.campusmate.entity.AddressBookEntry;
import org.pollub.campusmate.exception.AddressBookEntryNotFound;
import org.pollub.campusmate.repository.AddressBookEntryRepository;
import org.springframework.stereotype.Service;

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

    public AddressBookEntry getAddressBookEntryByContactName(String contactName) {
        return addressBookEntryRepository.findByContactName(contactName)
                .orElseThrow(() -> new AddressBookEntryNotFound("Entry with name " + contactName + " not found"));
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
    public void updateAddressBookEntry(long addressBookEntryId, AddressBookEntry addressBookEntry) {
        if (!addressBookEntryRepository.existsById(addressBookEntryId)) {
            throw new AddressBookEntryNotFound("Cannot execute update operation. Entry with id " + addressBookEntryId + " not found");
        }
        AddressBookEntry foundAddressBookEntry = getAddressBookEntryById(addressBookEntryId);
        addressBookEntry.setEntryId(addressBookEntryId);
        addressBookEntry.setAddressBook(foundAddressBookEntry.getAddressBook());
        addressBookEntryRepository.save(addressBookEntry);
    }

    public List<AddressBookEntry> getAllAddressBookEntries() {
        List<AddressBookEntry> foundEntries = (List<AddressBookEntry>)addressBookEntryRepository.findAll();

        if(foundEntries.isEmpty()){
            throw new AddressBookEntryNotFound("No address book entries found");
        }

        return foundEntries;
    }
}