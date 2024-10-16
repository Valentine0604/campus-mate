package org.pollub.campusmate.repository;

import org.pollub.campusmate.entity.AddressBookEntry;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressBookEntryRepository extends CrudRepository<AddressBookEntry, Long> {

    AddressBookEntry findByContactNameAndEntryId(String contactName, Long entryId);

    void deleteByContactName(String contactName);

    boolean existsByEntryId(Long entryId);

    boolean existsByContactName(String contactName);
}
