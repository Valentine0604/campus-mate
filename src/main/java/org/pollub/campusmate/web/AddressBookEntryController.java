package org.pollub.campusmate.web;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.pollub.campusmate.dto.AddressBookEntryCreationDTO;
import org.pollub.campusmate.dto.AddressBookEntryDTO;
import org.pollub.campusmate.entity.AddressBook;
import org.pollub.campusmate.entity.AddressBookEntry;
import org.pollub.campusmate.service.AddressBookEntryService;
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
    public ResponseEntity<AddressBookEntryDTO> getAddressBookEntryById(@PathVariable long addressBookEntryId) {
        AddressBookEntry addressBookEntry = addressBookEntryService.getAddressBookEntryById(addressBookEntryId);
        AddressBookEntryDTO addressBookEntryDTO = modelMapper.map(addressBookEntry, AddressBookEntryDTO.class);
        return new ResponseEntity<>(addressBookEntryDTO, HttpStatus.OK);
    }

    @GetMapping("/{contactName}")
    public ResponseEntity<AddressBookEntryDTO> getAddressBookEntryByContactName(@PathVariable String contactName) {
        AddressBookEntry addressBookEntry = addressBookEntryService.getAddressBookEntryByContactName(contactName);
        AddressBookEntryDTO addressBookEntryDTO = modelMapper.map(addressBookEntry, AddressBookEntryDTO.class);
        return new ResponseEntity<>(addressBookEntryDTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> createAddressBookEntry(@Valid @RequestBody AddressBookEntryCreationDTO addressBookEntryDTO, BindingResult bindingResult) {
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
    public ResponseEntity<String> updateAddressBookEntry(@PathVariable long addressBookEntryId, @RequestBody AddressBookEntryCreationDTO addressBookEntryDTO) {
        AddressBookEntry addressBookEntry = modelMapper.map(addressBookEntryDTO, AddressBookEntry.class);
        addressBookEntryService.updateAddressBookEntry(addressBookEntryId, addressBookEntry);
        return new ResponseEntity<>("Entry updated successfully",HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<AddressBookEntryDTO>> getAllAddressBookEntries() {
        return new ResponseEntity<>(addressBookEntryService.getAllAddressBookEntries().stream()
                .map(addressBookEntry -> modelMapper.map(addressBookEntry, AddressBookEntryDTO.class)).toList(), HttpStatus.OK);
    }
}
