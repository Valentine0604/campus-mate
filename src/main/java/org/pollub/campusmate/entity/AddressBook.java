package org.pollub.campusmate.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "address_book")
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class AddressBook {

    @Id
    @Column(name = "book_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookId;

    @NonNull
    @Column(name = "book_name", length = 50)
    @Size(message = "Name cannot be longer than 50 characters", max = 50)
    private String bookName;

}