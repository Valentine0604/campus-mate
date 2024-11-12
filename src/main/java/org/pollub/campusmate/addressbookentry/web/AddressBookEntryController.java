package org.pollub.campusmate.addressbookentry.web;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.pollub.campusmate.addressbookentry.dto.AddressBookEntryCreationDto;
import org.pollub.campusmate.addressbookentry.dto.AddressBookEntryDto;
import org.pollub.campusmate.addressbookentry.service.AddressBookEntryService;
import org.pollub.campusmate.addressbookentry.entity.AddressBookEntry;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/addressBook/entries")
public class AddressBookEntryController {

    private final ModelMapper modelMapper;
    AddressBookEntryService addressBookEntryService;

    @GetMapping("/{addressBookEntryId}")
    public ResponseEntity<AddressBookEntryDto> getAddressBookEntryById(@PathVariable long addressBookEntryId) {
        AddressBookEntry addressBookEntry = addressBookEntryService.getAddressBookEntryById(addressBookEntryId);
        AddressBookEntryDto addressBookEntryDTO = modelMapper.map(addressBookEntry, AddressBookEntryDto.class);
        return new ResponseEntity<>(addressBookEntryDTO, HttpStatus.OK);
    }

    @GetMapping("/{contactName}")
    public ResponseEntity<AddressBookEntryDto> getAddressBookEntryByContactName(@PathVariable String contactName) {
        AddressBookEntry addressBookEntry = addressBookEntryService.getAddressBookEntryByContactName(contactName);
        AddressBookEntryDto addressBookEntryDTO = modelMapper.map(addressBookEntry, AddressBookEntryDto.class);
        return new ResponseEntity<>(addressBookEntryDTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> createAddressBookEntry(@Valid @RequestBody AddressBookEntryCreationDto addressBookEntryDTO, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            bindingResult.getAllErrors().forEach(error -> errorMessage.append(error.getDefaultMessage()).append("\n"));
            return new ResponseEntity<>(errorMessage.toString(), HttpStatus.BAD_REQUEST);
        }
        AddressBookEntry addressBookEntry = modelMapper.map(addressBookEntryDTO, AddressBookEntry.class);
        addressBookEntryService.saveAddressBookEntry(addressBookEntry);
        return new ResponseEntity<>("Entry created successfully",HttpStatus.CREATED);
    }

    //TODO: Add entry with userId

    @DeleteMapping("/{addressBookEntryId}")
    public ResponseEntity<String> deleteAddressBookEntry(@PathVariable long addressBookEntryId) {
        addressBookEntryService.deleteAddressBookEntry(addressBookEntryId);
        return new ResponseEntity<>("Entry deleted successfully",HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{addressBookEntryId}")
    public ResponseEntity<String> updateAddressBookEntry(@PathVariable long addressBookEntryId, @RequestBody AddressBookEntryCreationDto addressBookEntryDTO) {
        AddressBookEntry addressBookEntry = modelMapper.map(addressBookEntryDTO, AddressBookEntry.class);
        addressBookEntryService.updateAddressBookEntry(addressBookEntryId, addressBookEntry);
        return new ResponseEntity<>("Entry updated successfully",HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<AddressBookEntryDto>> getAllAddressBookEntries() {
        return new ResponseEntity<>(addressBookEntryService.getAllAddressBookEntries().stream()
                .map(addressBookEntry -> modelMapper.map(addressBookEntry, AddressBookEntryDto.class)).toList(), HttpStatus.OK);
    }
}
