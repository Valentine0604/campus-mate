package org.pollub.campusmate.web;

import lombok.AllArgsConstructor;
import org.pollub.campusmate.entity.AddressBookEntry;
import org.pollub.campusmate.service.AddressBookEntryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/entries")
public class AddressBookEntryController {

    AddressBookEntryService addressBookEntryService;

    @GetMapping("/{addressBookEntryId}")
    public ResponseEntity<AddressBookEntry> getAddressBookEntry(@PathVariable long addressBookEntryId) {
        return new ResponseEntity<>(addressBookEntryService.getAddressBookEntry(addressBookEntryId), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<AddressBookEntry> createAddressBookEntry(@RequestBody AddressBookEntry addressBookEntry) {
        return new ResponseEntity<>(addressBookEntryService.saveAddressBookEntry(addressBookEntry), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{addressBookEntryId}")
    public ResponseEntity<HttpStatus> deleteAddressBookEntry(@PathVariable long addressBookEntryId) {
        addressBookEntryService.deleteAddressBookEntry(addressBookEntryId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<AddressBookEntry>> getAllAddressBookEntries() {
        return new ResponseEntity<>(addressBookEntryService.getAllAddressBookEntries(), HttpStatus.OK);
    }
}
