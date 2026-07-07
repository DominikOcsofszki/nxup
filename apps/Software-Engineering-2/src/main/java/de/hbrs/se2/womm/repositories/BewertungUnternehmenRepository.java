package de.hbrs.se2.womm.repositories;

import de.hbrs.se2.womm.entities.BewertungUnternehmen;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BewertungUnternehmenRepository extends JpaRepository<BewertungUnternehmen, Long> {
    BewertungUnternehmen findBewertungUnternehmenByBewertungUnternehmenId(long bewertungUnternehmenID);

    List<BewertungUnternehmen> findBewertungStudentByStudent_StudentId(long studentID);

    List<BewertungUnternehmen> findBewertungStudentByUnternehmen_UnternehmenId(long unternehmenID);
}
