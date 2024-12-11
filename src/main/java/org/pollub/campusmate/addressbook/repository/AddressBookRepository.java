package org.pollub.campusmate.addressbook.repository;

import org.pollub.campusmate.addressbook.entity.AddressBook;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressBookRepository extends CrudRepository<AddressBook, Long> {


    Optional<AddressBook> findByBookName(String pollubAddressBook);
}
