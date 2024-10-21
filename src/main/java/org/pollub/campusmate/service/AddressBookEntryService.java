package org.pollub.campusmate.service;

import lombok.AllArgsConstructor;
import org.pollub.campusmate.repository.AddressBookEntryRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AddressBookEntryService {

    private AddressBookEntryRepository addressBookEntryRepository;
}