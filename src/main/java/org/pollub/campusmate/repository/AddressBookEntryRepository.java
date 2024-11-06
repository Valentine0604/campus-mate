package org.pollub.campusmate.repository;

import org.pollub.campusmate.entity.AddressBookEntry;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressBookEntryRepository extends CrudRepository<AddressBookEntry, Long> {

    Optional<AddressBookEntry> findByContactName(String contactName);
}
