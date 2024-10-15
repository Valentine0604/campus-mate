package org.pollub.campusmate.repository;

import org.pollub.campusmate.entity.AddressBook;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressBookRepository extends CrudRepository<AddressBook, Long> {

    AddressBook findByBookName(String bookName);

    AddressBook findByBookIdAndBookName(Long bookId, String bookName);

    void deleteByBookIdAndBookName(Long bookId, String bookName);

    void deleteByBookName(String bookName);

    boolean existsByBookName(String bookName);

    boolean existsByBookId(Long bookId);

}
