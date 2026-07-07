package de.hbrs.se2.womm.repositories;

import de.hbrs.se2.womm.entities.Unternehmen;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UnternehmenRepository extends JpaRepository<Unternehmen, Long> {
    Optional<Unternehmen> findUnternehmenByUnternehmenId(long unternehmenID);

    Optional<Unternehmen> findUnternehmenByNutzer_NutzerId(long nutzerID);

    List<Unternehmen> findUnternehmenByNameIgnoreCaseContaining(String unternehmenName);
}
