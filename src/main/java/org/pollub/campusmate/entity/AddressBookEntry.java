package org.pollub.campusmate.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "address_book_entry")
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class AddressBookEntry {

    @Id
    @Column(name = "entry_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long entryId;

    @NonNull
    @Column(name = "contact_name", length = 100)
    @Size(message = "Contact name cannot be longer than 100 characters", max = 100)
    private String contactName;

    @NonNull
    @Column(name = "email")
    @Email
    private String email;

    //TODO: phone number validation

    @NonNull
    @Column(name = "phone_number", length = 9)
    @Size(message = "Phone number cannot be longer than 9 characters", max = 9)
    private String phoneNumber;

    @NonNull
    @Column(name = "class_number", length = 5)
    private String classNumber;

    @Column(name = "notes", length = 200)
    @Size(message = "Notes cannot be longer than 200 characters", max = 200)
    private String notes;

}