package org.pollub.campusmate;

import lombok.AllArgsConstructor;
import org.pollub.campusmate.addressbook.entity.AddressBook;
import org.pollub.campusmate.addressbook.service.AddressBookService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@AllArgsConstructor
public class CampusMateApplication {

    private final AddressBookService addressBookService;

    @Bean
    public CommandLineRunner createAddressBook(AddressBookService addressBookService) {
        return args -> {
            AddressBook addressBook = new AddressBook("Pollub Address Book");
            addressBookService.createAddressBook(addressBook);
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(CampusMateApplication.class, args);
    }
}


