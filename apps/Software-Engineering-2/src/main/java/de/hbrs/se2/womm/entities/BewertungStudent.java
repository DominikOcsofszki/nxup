package de.hbrs.se2.womm.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "bewertung_student")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BewertungStudent {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "bewertung_student_id")
    private Integer bewertungStudentId;

    @Column(name = "bewertung_student_wertung")
    private Integer bewertungStudentWertung;

    @Column(name = "bewertung_student_text", columnDefinition = "text")
    private String bewertungStudentText;

    @ManyToOne
    @JoinColumn(name = "unternehmen_id")
    private Unternehmen unternehmen;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

}

