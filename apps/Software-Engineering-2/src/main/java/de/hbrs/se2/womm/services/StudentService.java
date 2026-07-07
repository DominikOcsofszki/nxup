package de.hbrs.se2.womm.services;

import de.hbrs.se2.womm.dtos.StudentDTO;
import de.hbrs.se2.womm.entities.Nutzer;
import de.hbrs.se2.womm.entities.Student;
import de.hbrs.se2.womm.mapper.NutzerMapper;
import de.hbrs.se2.womm.mapper.StudentMapper;
import de.hbrs.se2.womm.repositories.NutzerRepository;
import de.hbrs.se2.womm.repositories.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService{
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper = StudentMapper.INSTANCE;
    private final NutzerRepository nutzerRepository;
    private  final NutzerMapper nutzerMapper = NutzerMapper.INSTANCE;

    public StudentService(StudentRepository studentRepository, NutzerRepository nutzerRepository) {
        this.nutzerRepository = nutzerRepository;
        this.studentRepository = studentRepository;
    }

    public List<StudentDTO> getAlleStudenten() {
        return studentRepository
                .findAll()
                .stream()
                .map(studentMapper::studentToStudentDto)
                .toList();
    }

    public Optional<StudentDTO> getById(Long id) {
        return studentRepository
                .findById(id)
                .map(studentMapper::studentToStudentDto);
    }

    public void saveStudent(StudentDTO studentDTO) {
        Nutzer nutzer = nutzerMapper.nutzerDTOToNutzer(studentDTO.getNutzer());
        Student student = studentMapper.studentDtoToStudent(studentDTO);
        nutzerRepository.save(nutzer);
        studentRepository.save(student);
    }

    public StudentDTO getByNutzerId(Long id) {
        Student student =  studentRepository.findStudentByNutzer_NutzerId(id);
        return studentMapper.studentToStudentDto(student);
    }
}
