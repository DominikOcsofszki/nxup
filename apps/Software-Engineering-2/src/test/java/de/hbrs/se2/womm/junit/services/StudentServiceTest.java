package de.hbrs.se2.womm.junit.services;

import de.hbrs.se2.womm.dtos.NutzerDTO;
import de.hbrs.se2.womm.dtos.StudentDTO;
import de.hbrs.se2.womm.entities.Nutzer;
import de.hbrs.se2.womm.entities.Student;
import de.hbrs.se2.womm.repositories.NutzerRepository;
import de.hbrs.se2.womm.repositories.StudentRepository;
import de.hbrs.se2.womm.services.StudentService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class StudentServiceTest {
    private StudentRepository srepo;
    private NutzerRepository nrepo;
    private StudentService service;

    private List<Nutzer> nutzerEntityList;

    private List<StudentDTO> studentDTOList;
    private List<Student> studentEntityList;

    private StudentDTO studentDTO;

    private Student student;

    @BeforeEach
    void setup() {
        srepo = mock(StudentRepository.class);
        nrepo = mock(NutzerRepository.class);
        service = new StudentService(srepo, nrepo);
        nutzerEntityList = new ArrayList<>();
        studentDTOList = new ArrayList<>();
        studentEntityList = new ArrayList<>();

        studentDTO = StudentDTO.builder()
                .nutzer(NutzerDTO.builder().nutzerId(101L).build())
                .studentId(100L)
                .studentVorname("Vorname")
                .studentName("Nachname")
                .studentGeburtstag("1999.01.01")
                .studentBenachrichtigung(true)
                .studentBio("bio")
                .studentSpezialisierung("spezial")
                .studentSemester(9)
                .build();

        student = Student.builder()
                .nutzer(Nutzer.builder().nutzerId(101L).build())
                .studentId(100L)
                .studentVorname("Vorname")
                .studentName("Nachname")
                .studentGeburtstag("1999.01.01")
                .studentBenachrichtigung(true)
                .studentBio("bio")
                .studentSpezialisierung("spezial")
                .studentSemester(9)
                .build();
    }

    @AfterEach
    void teardown() {
        srepo = null;
        nrepo = null;
        service = null;
        nutzerEntityList = null;
        studentDTOList = null;
        studentEntityList = null;
        studentDTO = null;
        student = null;
    }

    @Test
    void testGetAlleStudenten() {
        when(srepo.findAll()).thenReturn(studentEntityList);

        List<StudentDTO> actual = service.getAlleStudenten();
        assertNotNull(actual);
        assertEquals(0, actual.size());

        studentEntityList.add(student);
        actual = service.getAlleStudenten();
        assertNotNull(actual);
        assertEquals(1, actual.size());
        assertTrue(studentDTO.equals(actual.get(0)));
    }

    @Test
    void testGetById() {
        when(srepo.findById(any(Long.class))).thenAnswer(
                i -> {
                    List<Student> curr = studentEntityList
                            .stream()
                            .filter(p -> p.getStudentId().toString().equals(i.getArgument(0).toString()))
                            .toList();
                    return curr.size() == 0 ? Optional.empty() : Optional.of(curr.get(0));
                }
        );

        Optional<StudentDTO> actual = service.getById(100L);
        assertTrue(actual.isEmpty());

        studentEntityList.add(student);

        actual = service.getById(100L);
        assertFalse(actual.isEmpty());
        assertEquals(studentDTO.getStudentId(), actual.get().getStudentId());

        Student student_2 = Student.builder()
                .nutzer(null)
                .studentId(999L)
                .studentVorname("Vorname")
                .studentName("Nachname")
                .studentGeburtstag("1999.02.01")
                .studentBenachrichtigung(true)
                .studentBio("bio")
                .studentSpezialisierung("spezial")
                .studentSemester(9)
                .build();

        studentEntityList.add(student_2);

        actual = service.getById(999L);
        assertFalse(actual.isEmpty());
        assertEquals(student_2.getStudentId(), actual.get().getStudentId());
    }

    @Test
    void testSaveStudent() {
        when(srepo.save(any(Student.class))).thenAnswer(
                i -> {
                    Student input = (Student) i.getArgument(0);
                    studentEntityList.add(input);
                    return input;
                }
        );
        when(nrepo.save(any(Nutzer.class))).thenAnswer(
                i -> {
                    Nutzer input = (Nutzer) i.getArgument(0);
                    nutzerEntityList.add(input);
                    return input;
                }
        );

        assertEquals(0, studentEntityList.size());
        assertEquals(0, nutzerEntityList.size());

        service.saveStudent(studentDTO);
        assertEquals(1, studentEntityList.size());
        assertEquals(1, nutzerEntityList.size());

        student = studentEntityList.get(0);
        assertEquals(studentDTO.getStudentId(), student.getStudentId());
        assertEquals(studentDTO.getNutzer().getNutzerId(), student.getNutzer().getNutzerId());
        assertEquals(studentDTO.getStudentSemester(), student.getStudentSemester());
        assertEquals(studentDTO.getStudentName(), student.getStudentName());
        assertEquals(studentDTO.getStudentBio(), student.getStudentBio());
        assertEquals(studentDTO.getStudentVorname(), student.getStudentVorname());
        assertEquals(studentDTO.getStudentGeburtstag(), student.getStudentGeburtstag());
        assertEquals(studentDTO.getStudentSpezialisierung(), student.getStudentSpezialisierung());
        assertEquals(studentDTO.isStudentBenachrichtigung(), student.getStudentBenachrichtigung());
    }

    @Test
    void testGetByNutzerId() {
        when(srepo.findStudentByNutzer_NutzerId(any(Long.class))).thenAnswer(
                i -> {
                    List<Student> curr = studentEntityList
                            .stream()
                            .filter(p -> p.getNutzer().getNutzerId() == (long) i.getArgument(0))
                            .toList();
                    return curr.size() == 0 ? null : curr.get(0);
                }
        );

        StudentDTO actual = service.getByNutzerId(101L);
        assertNull(actual);

        studentEntityList.add(student);

        actual = service.getByNutzerId(101L);
        assertEquals(studentDTO.getStudentId(), actual.getStudentId());

        Student student_2 = Student.builder().nutzer(Nutzer.builder().nutzerId(102L).build()).build();
        studentEntityList.add(student_2);

        actual = service.getByNutzerId(101L);
        assertEquals(studentDTO.getStudentId(), actual.getStudentId());
        assertEquals(studentDTO.getNutzer().getNutzerId(),actual.getNutzer().getNutzerId());
    }
}
