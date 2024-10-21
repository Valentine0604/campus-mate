package org.pollub.campusmate.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;


@Entity
@Table(name = "grade")
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class Grade {

    @Id
    @Column(name = "grade_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gradeId;

    @NonNull
    @Column(name = "subject_name", length = 100)
    @Size(message = "Subject cannot be longer than 100 characters", max = 100)
    private String subjectName;

    //TODO: grade validator

    @NonNull
    @Column(name = "grade", length = 1)
    @Size(message = "Grade must be a single character", max = 1)
    private String grade;

    @Column(name = "comment", length = 100)
    @Size(message = "Comment cannot be longer than 100 characters", max = 100)
    private String comment;

    @NonNull
    @Column(name = "date_of_receipt", updatable = false)
    private LocalDate dateOfReceipt;

    @PrePersist
    protected void onCreate() {
        this.dateOfReceipt = LocalDate.now();
    }
}