package de.hbrs.se2.womm.repositories;

import de.hbrs.se2.womm.entities.Stelle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StelleRepository extends JpaRepository<Stelle, Long> {
    List<Stelle> findByUnternehmen_UnternehmenId(long stelleID);

    List<Stelle> findByUnternehmen_Nutzer_NutzerId(long id);

    List<Stelle> findByStelleTitelIsContainingIgnoreCase(String titel);

    List<Stelle> findByStelleOrtIsContainingIgnoreCase(String ort);

    List<Stelle> findByStelleBeschreibungIsContainingIgnoreCase(String beschreibung);

    List<Stelle> findByStelleWebsiteIsContainingIgnoreCase(String website);

    List<Stelle> findByUnternehmen_NameIsContainingIgnoreCase(String unternehmenName);
}
