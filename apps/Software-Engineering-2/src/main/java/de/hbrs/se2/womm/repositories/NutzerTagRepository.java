package de.hbrs.se2.womm.repositories;

import de.hbrs.se2.womm.entities.NutzerTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NutzerTagRepository extends JpaRepository<NutzerTag, Long> {
    List<NutzerTag> findNutzerTagByNutzer_NutzerId(long nutzerID);

    List<NutzerTag> findNutzerTagByTag_TagId(long tagID);
}