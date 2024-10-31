package org.pollub.campusmate.web;

import lombok.AllArgsConstructor;
import org.pollub.campusmate.entity.AddressBook;
import org.pollub.campusmate.service.AddressBookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/addressbook")
public class AddressBookController {

    AddressBookService addressBookService;

    @GetMapping("/{addressBookId}")
    public ResponseEntity<AddressBook> getAddressBook(@PathVariable Long addressBookId) {
        return new ResponseEntity<>(addressBookService.getAddressBookRepository(addressBookId), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<AddressBook> createAddressBook(@RequestBody AddressBook addressBook) {
        return new ResponseEntity<>(addressBookService.createAddressBook(addressBook), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{addressBookId}")
    public ResponseEntity<HttpStatus> deleteAddressBook(@PathVariable Long addressBookId) {
        addressBookService.deleteAddressBook(addressBookId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/all")
    public ResponseEntity<List<AddressBook>> getAllAddressBooks() {
        return new ResponseEntity<>(addressBookService.getAllAddressBooks(), HttpStatus.OK);
    }
}

