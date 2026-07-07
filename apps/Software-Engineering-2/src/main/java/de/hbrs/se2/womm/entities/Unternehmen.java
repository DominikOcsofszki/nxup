package de.hbrs.se2.womm.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "unternehmen")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Unternehmen {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "unternehmen_id")
    private Integer unternehmenId;

    @Column(name = "unternehmen_name", nullable = false)
    private String name;

    @Column(name = "unternehmen_beschreibung")
    private String beschreibung;

    @Column(name = "unternehmen_gruendung")
    private String gruendung;

    @Column(name = "unternehmen_website")
    private String website_url;

    @OneToOne
    @JoinColumn(name = "nutzer_id")
    private Nutzer nutzer;
}

