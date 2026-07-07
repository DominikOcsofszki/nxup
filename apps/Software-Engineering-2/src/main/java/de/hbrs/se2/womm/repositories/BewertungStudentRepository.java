package de.hbrs.se2.womm.repositories;

import de.hbrs.se2.womm.entities.BewertungStudent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BewertungStudentRepository extends JpaRepository<BewertungStudent, Long> {
    BewertungStudent findBewertungStudentByBewertungStudentId(long bewertungStudentID);

    List<BewertungStudent> findBewertungStudentByStudent_StudentId(long studentID);

    List<BewertungStudent> findBewertungStudentByUnternehmen_UnternehmenId(long unternehmenID);
}
