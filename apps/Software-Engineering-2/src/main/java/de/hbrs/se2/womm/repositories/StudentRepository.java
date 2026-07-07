package de.hbrs.se2.womm.repositories;

import de.hbrs.se2.womm.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Student findStudentByStudentId(long studentID);

    Student findStudentByNutzer_NutzerId(long nutzerID);

    List<Student> findStudentByStudentNameContaining(String studentName);

    List<Student> findStudentByStudentVornameContaining(String studentVorname);
}
