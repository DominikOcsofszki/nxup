package de.hbrs.se2.womm.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Table(name = "benachrichtigung")
@Entity
public class Benachrichtigung {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "nachricht")
    private String nachricht;

    //If false the message hasn't been read
    @Column(name = "gelesen")
    private boolean gelesen;

    @Column(name = "datum")
    private Date datum;

    @ManyToOne
    @JoinColumn(name = "nutzer_id")
    private Nutzer nutzer;
}
