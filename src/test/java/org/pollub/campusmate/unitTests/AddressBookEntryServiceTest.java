package org.pollub.campusmate.unitTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.pollub.campusmate.addressbookentry.dto.AddressBookEntryDto;
import org.pollub.campusmate.addressbookentry.entity.AddressBookEntry;
import org.pollub.campusmate.addressbookentry.exception.AddressBookEntryNotFound;
import org.pollub.campusmate.addressbookentry.repository.AddressBookEntryRepository;
import org.pollub.campusmate.addressbookentry.service.AddressBookEntryService;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddressBookEntryServiceTest {
    @Mock
    private AddressBookEntryRepository addressBookEntryRepository;

    @InjectMocks
    private AddressBookEntryService addressBookEntryService;

    private AddressBookEntry testEntry;
    private AddressBookEntryDto testEntryDto;

    @BeforeEach
    void setUp() {
        testEntry = new AddressBookEntry();
        testEntry.setEntryId(1L);
        testEntry.setContactName("John Doe");
        testEntry.setEmail("john@example.com");
        testEntry.setPhoneNumber("123456789");
        testEntry.setClassNumber("A1");
        testEntry.setNotes("Test notes");

        testEntryDto = new AddressBookEntryDto(
                1L,
                "John Doe",
                "john@example.com",
                "123456789",
                "A1",
                "Test notes",
                1L
        );
    }

    @Test
    void getAddressBookEntryById_ShouldReturnEntry() {
        when(addressBookEntryRepository.findById(1L)).thenReturn(Optional.of(testEntry));

        AddressBookEntry result = addressBookEntryService.getAddressBookEntryById(1L);

        assertNotNull(result);
        assertEquals("John Doe", result.getContactName());
        assertEquals("john@example.com", result.getEmail());
    }

    @Test
    void getAddressBookEntryById_ShouldThrowException_WhenNotFound() {
        when(addressBookEntryRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(AddressBookEntryNotFound.class,
                () -> addressBookEntryService.getAddressBookEntryById(999L));
    }

    @Test
    void searchAddressBookEntriesByContactName_ShouldReturnMatchingEntries() {
        when(addressBookEntryRepository.findByContactNameContainingIgnoreCase("John"))
                .thenReturn(Optional.of(Collections.singletonList(testEntry)));

        List<AddressBookEntry> results = addressBookEntryService
                .searchAddressBookEntriesByContactName("John");

        assertFalse(results.isEmpty());
        assertEquals(1, results.size());
        assertEquals("John Doe", results.get(0).getContactName());
    }

    @Test
    void saveAddressBookEntry_ShouldSaveEntry() {
        when(addressBookEntryRepository.save(any(AddressBookEntry.class)))
                .thenReturn(testEntry);

        addressBookEntryService.saveAddressBookEntry(testEntry);

        verify(addressBookEntryRepository).save(testEntry);
    }

    @Test
    void updateAddressBookEntry_ShouldUpdateEntry() {
        when(addressBookEntryRepository.existsById(1L)).thenReturn(true);
        when(addressBookEntryRepository.findById(1L)).thenReturn(Optional.of(testEntry));

        addressBookEntryService.updateAddressBookEntry(1L, testEntryDto);

        verify(addressBookEntryRepository).save(any(AddressBookEntry.class));
    }

    @Test
    void getAllAddressBookEntries_ShouldReturnAllEntries() {
        when(addressBookEntryRepository.findAll())
                .thenReturn(Collections.singletonList(testEntry));

        List<AddressBookEntry> results = addressBookEntryService.getAllAddressBookEntries();

        assertFalse(results.isEmpty());
        assertEquals(1, results.size());
    }
}
