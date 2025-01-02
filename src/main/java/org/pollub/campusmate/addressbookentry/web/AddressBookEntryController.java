package org.pollub.campusmate.addressbookentry.web;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.pollub.campusmate.addressbookentry.dto.AddressBookEntryCreationDto;
import org.pollub.campusmate.addressbookentry.dto.AddressBookEntryDto;
import org.pollub.campusmate.addressbookentry.mapper.AddressBookEntryCreationMapper;
import org.pollub.campusmate.addressbookentry.mapper.AddressBookEntryMapper;
import org.pollub.campusmate.addressbookentry.service.AddressBookEntryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/api/addressBook/entries")
public class AddressBookEntryController {

    private final AddressBookEntryMapper addressBookEntryMapper;
    private final AddressBookEntryCreationMapper addressBookEntryCreationMapper;
    private final AddressBookEntryService addressBookEntryService;

    @GetMapping("/{addressBookEntryId}")
    public ResponseEntity<AddressBookEntryDto> getAddressBookEntryById(@PathVariable long addressBookEntryId) {
        var addressBookEntry = addressBookEntryService.getAddressBookEntryById(addressBookEntryId);
        var addressBookEntryDto = addressBookEntryMapper.toDto(addressBookEntry);
        return new ResponseEntity<>(addressBookEntryDto, HttpStatus.OK);
    }

    @GetMapping("/search/{contactName}")
    public ResponseEntity<AddressBookEntryDto> getAddressBookEntryByContactName(@PathVariable String contactName) {
        var addressBookEntry = addressBookEntryService.getAddressBookEntryByContactName(contactName);
        var addressBookEntryDto = addressBookEntryMapper.toDto(addressBookEntry);
        return new ResponseEntity<>(addressBookEntryDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> createAddressBookEntry(
            @Valid @RequestBody AddressBookEntryCreationDto addressBookEntryCreationDto,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getAllErrors().stream()
                    .map(error -> error.getDefaultMessage() + "\n")
                    .collect(Collectors.joining());
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }
        var addressBookEntry = addressBookEntryCreationMapper.toEntity(addressBookEntryCreationDto);
        addressBookEntryService.saveAddressBookEntry(addressBookEntry);
        return new ResponseEntity<>("Entry created successfully", HttpStatus.CREATED);
    }

    @DeleteMapping("/{addressBookEntryId}")
    public ResponseEntity<String> deleteAddressBookEntry(@PathVariable long addressBookEntryId) {
        addressBookEntryService.deleteAddressBookEntry(addressBookEntryId);
        return new ResponseEntity<>("Entry deleted successfully", HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{addressBookEntryId}")
    public ResponseEntity<String> updateAddressBookEntry(
            @PathVariable long addressBookEntryId,
            @RequestBody AddressBookEntryCreationDto addressBookEntryCreationDto) {
        var addressBookEntry = addressBookEntryCreationMapper.toEntity(addressBookEntryCreationDto);
        addressBookEntryService.updateAddressBookEntry(addressBookEntryId, addressBookEntry);
        return new ResponseEntity<>("Entry updated successfully", HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<AddressBookEntryDto>> getAllAddressBookEntries() {
        var entries = addressBookEntryService.getAllAddressBookEntries().stream()
                .map(addressBookEntryMapper::toDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(entries, HttpStatus.OK);
    }
}