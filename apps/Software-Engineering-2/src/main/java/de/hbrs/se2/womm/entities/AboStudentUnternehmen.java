package de.hbrs.se2.womm.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "abo_student_unternehmen")
public class AboStudentUnternehmen {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "abo_id")
    private Integer aboId;

    @Column(name = "abo_benachrichtigungen")
    private Boolean aboBenachrichtigungen;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "unternehmen_id")
    private Unternehmen unternehmen;
}

