package de.hbrs.se2.womm.repositories;

import de.hbrs.se2.womm.entities.AboTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AboTagRepository extends JpaRepository<AboTag,Long> {
    AboTag findAboTagByStudent_StudentIdAndTag_TagId(long studentID, long tagID);
    List<AboTag> findAboTagByStudent_Nutzer_NutzerId(long id);
}
