package org.pollub.campusmate.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "address_book")
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class AddressBook {

    @Id
    @Column(name = "address_book_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookId;

    @NonNull
    @Column(name = "address_book_name", length = 50)
    @Size(message = "Name cannot be longer than 50 characters", max = 50)
    private String bookName;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    @OneToMany(mappedBy = "addressBook", cascade = CascadeType.ALL)
    private List<AddressBookEntry> entries;

}