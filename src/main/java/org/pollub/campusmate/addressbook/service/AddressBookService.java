package org.pollub.campusmate.addressbook.service;

import lombok.AllArgsConstructor;
import org.pollub.campusmate.addressbook.entity.AddressBook;
import org.pollub.campusmate.addressbook.exception.AddressBookNotFound;
import org.pollub.campusmate.addressbook.repository.AddressBookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AddressBookService {

    private final AddressBookRepository addressBookRepository;

    public AddressBook getAddressBookById(long addressBookId) {
        return addressBookRepository.findById(addressBookId)
                .orElseThrow(() -> new AddressBookNotFound("Address book with id " + addressBookId + " not found"));
    }

    public void createAddressBook(AddressBook addressBook) {
        addressBookRepository.save(addressBook);
    }

    public void deleteAddressBook(long addressBookId) {
        if(!addressBookRepository.existsById(addressBookId)) {
            throw new AddressBookNotFound("Cannot execute delete operation. Address book with id " + addressBookId + " not found");
        }
        addressBookRepository.deleteById(addressBookId);
    }

    public void updateAddressBook(Long addressBookId, AddressBook addressBook) {
        if(!addressBookRepository.existsById(addressBookId)) {
            throw new AddressBookNotFound("Cannot execute update operation. Address book with id " + addressBookId + " not found");
        }
        AddressBook foundAddressBook = addressBookRepository.findById(addressBookId).get();
        addressBook.setBookId(addressBookId);
        addressBook.setEntries(foundAddressBook.getEntries());
        addressBookRepository.save(addressBook);
    }

    public List<AddressBook> getAllAddressBooks() {
        List<AddressBook> foundAddressBooks = (List<AddressBook>)addressBookRepository.findAll();

        if(foundAddressBooks.isEmpty()){
            throw new AddressBookNotFound("No address books found");
        }

        return foundAddressBooks;
    }


    public AddressBook getAddressBookByBookName(String addressBookName) {
        return addressBookRepository.findByBookName(addressBookName)
                .orElseThrow(() -> new AddressBookNotFound("Address book with name " + addressBookName + " not found"));

    }
}