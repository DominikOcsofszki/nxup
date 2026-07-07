package de.hbrs.se2.womm.mapper;

import de.hbrs.se2.womm.dtos.StudentDTO;
import de.hbrs.se2.womm.entities.Student;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface StudentMapper {

    StudentMapper INSTANCE = Mappers.getMapper(StudentMapper.class);

    StudentDTO studentToStudentDto(Student student);

    Student studentDtoToStudent(StudentDTO studentDTO);
}
