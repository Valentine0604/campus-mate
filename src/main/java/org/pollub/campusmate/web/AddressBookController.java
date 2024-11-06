package org.pollub.campusmate.web;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.pollub.campusmate.dto.AddressBookDTO;
import org.pollub.campusmate.entity.AddressBook;
import org.pollub.campusmate.service.AddressBookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/addressBook")
public class AddressBookController {

    private final AddressBookService addressBookService;
    private final ModelMapper modelMapper;


    @PostMapping
    public ResponseEntity<String> createAddressBook(@RequestBody AddressBook addressBook) {
        addressBookService.createAddressBook(addressBook);
        return new ResponseEntity<>("AddressBook added successfully", HttpStatus.CREATED);
    }

    @DeleteMapping("/{addressBookId}")
    public ResponseEntity<String> deleteAddressBook(@PathVariable Long addressBookId) {
        addressBookService.deleteAddressBook(addressBookId);
        return new ResponseEntity<>("AddressBook deleted successfully", HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{addressBookId}")
    public ResponseEntity<String> updateAddressBook(@PathVariable Long addressBookId, @RequestBody AddressBookDTO addressBookDTO) {
        AddressBook addressBook = modelMapper.map(addressBookDTO, AddressBook.class);
        addressBookService.updateAddressBook(addressBookId, addressBook);
        return new ResponseEntity<>("AddressBook updated successfully", HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<AddressBookDTO>> getAllAddressBooks() {
        return new ResponseEntity<>(addressBookService.getAllAddressBooks().stream().map(addressBook -> modelMapper.map(addressBook, AddressBookDTO.class)).toList(), HttpStatus.OK);
    }


}

