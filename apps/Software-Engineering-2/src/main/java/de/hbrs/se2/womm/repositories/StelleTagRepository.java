package de.hbrs.se2.womm.repositories;

import de.hbrs.se2.womm.entities.StelleTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StelleTagRepository extends JpaRepository<StelleTag, Long> {
    List<StelleTag> findStelleTagByTag_TagId(long tagID);

    List<StelleTag> findStelleTagByStelle_StelleTitelContaining(String stelleTitel);

    StelleTag findStelleTagByStelleTagId(long stelleTagID);
}
