package de.hbrs.se2.womm.repositories;

import de.hbrs.se2.womm.entities.Nutzer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NutzerRepository extends JpaRepository<Nutzer, Long> {
    Nutzer findByNutzerId(long nutzerID);
    Nutzer findNutzerByNutzerMail(String mail);
    List<Nutzer> findByNutzerMailContaining(String mail);
}
