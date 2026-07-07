package de.hbrs.se2.womm.repositories;

import de.hbrs.se2.womm.entities.AboStudentUnternehmen;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AboStudentUnternehmenRepository extends JpaRepository<AboStudentUnternehmen,Long> {
    AboStudentUnternehmen findAboStudentUnternehmenByAboId(long aboID);
    List<AboStudentUnternehmen> findByStudent_NutzerNutzerIdOrUnternehmen_Nutzer_NutzerId(long s_id, long u_id);

    List<AboStudentUnternehmen> findByUnternehmen_UnternehmenId(Long id);
    List<AboStudentUnternehmen> findAboStudentUnternehmenByStudent_StudentId(long studentID);
}
