package de.hbrs.se2.womm.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "student")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "student_id")
    private Long studentId;

    @Column(name = "student_vorname", nullable = false)
    private String studentVorname;

    @Column(name = "student_name", nullable = false)
    private String studentName;

    @Column(name = "student_geburtstag", nullable = false)
    private String studentGeburtstag;

    @Column(name = "student_benachrichtigung", nullable = false)
    private Boolean studentBenachrichtigung;

    @Column(name = "student_bio")
    private String studentBio;

    @Column(name = "student_spezialisierung")
    private String studentSpezialisierung;

    @Column(name = "student_semester")
    private Integer studentSemester;

    @OneToOne
    @JoinColumn(name = "nutzer_id")
    private Nutzer nutzer;

}

