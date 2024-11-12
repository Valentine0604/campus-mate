package org.pollub.campusmate.addressbook.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.pollub.campusmate.addressbookentry.entity.AddressBookEntry;

import java.util.List;

@Entity
@Table(name = "address_book")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressBook {

    @Id
    @Column(name = "address_book_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookId;

    @NonNull
    @Column(name = "address_book_name", length = 50)
    @Size(message = "Name cannot be longer than 50 characters", max = 50)
    private String bookName;

    @OneToMany(mappedBy = "addressBook", cascade = CascadeType.ALL)
    private List<AddressBookEntry> entries;

    public AddressBook(String bookName) {
        this.bookName = bookName;
    }
}