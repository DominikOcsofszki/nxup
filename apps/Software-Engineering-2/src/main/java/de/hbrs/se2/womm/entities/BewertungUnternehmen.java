package de.hbrs.se2.womm.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "bewertung_unternehmen")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BewertungUnternehmen {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "bewertung_unternehmen_id")
    private Integer bewertungUnternehmenId;

    @Column(name = "bewertung_unternehmen_wertung")
    private Integer bewertungUnternehmenWertung;

    @Column(name = "bewertung_unternehmen_text", columnDefinition = "text")
    private String bewertungUnternehmenText;

    @ManyToOne
    @JoinColumn(name = "unternehmen_id")
    private Unternehmen unternehmen;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

}

