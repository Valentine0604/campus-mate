package org.pollub.campusmate.service;

import lombok.AllArgsConstructor;
import org.pollub.campusmate.entity.AddressBook;
import org.pollub.campusmate.repository.AddressBookRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class AddressBookService {

    //TODO: exception handling

    private final AddressBookRepository addressBookRepository;

    public AddressBook getAddressBookRepository(long addressBookId) {
        return addressBookRepository.findById(addressBookId).orElseThrow(null);
    }

    public void createAddressBook(AddressBook addressBook) {
        addressBookRepository.save(addressBook);
    }

    public void deleteAddressBook(long addressBookId) {
//        if(!addressBookRepository.existsById(addressBookId)) {
//
//        }
        addressBookRepository.deleteById(addressBookId);
    }

    public List<AddressBook> getAllAddressBooks() {
        Iterable<AddressBook> addressBooks = addressBookRepository.findAll();
        List<AddressBook> foundAddressBooks = new ArrayList<>();

        addressBooks.forEach(foundAddressBooks::add);

        if(foundAddressBooks.isEmpty()){
            return null;
        }

        return foundAddressBooks;
    }

    //TODO: update address book method
}