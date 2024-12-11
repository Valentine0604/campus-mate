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

    public static void main(String[] args) {
        SpringApplication.run(CampusMateApplication.class, args);
    }
}


