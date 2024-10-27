package org.pollub.campusmate.service;

import lombok.AllArgsConstructor;
import org.pollub.campusmate.repository.AddressBookRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AddressBookService {

    private final AddressBookRepository addressBookRepository;
}