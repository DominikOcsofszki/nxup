package de.hbrs.se2.womm.repositories;

import de.hbrs.se2.womm.entities.Benachrichtigung;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BenachrichtigungRepository extends JpaRepository<Benachrichtigung, Long> {
    List<Benachrichtigung> findBenachrichtigungByNutzer_NutzerId(Long id);
    List<Benachrichtigung> findBenachrichtigungByNutzer_NutzerIdAndGelesenIsFalse(Long id);
}
