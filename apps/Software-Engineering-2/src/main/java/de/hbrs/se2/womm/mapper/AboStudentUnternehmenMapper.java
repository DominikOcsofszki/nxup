package de.hbrs.se2.womm.mapper;

import de.hbrs.se2.womm.dtos.AboDTO;
import de.hbrs.se2.womm.entities.AboStudentUnternehmen;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AboStudentUnternehmenMapper {
    AboStudentUnternehmenMapper INSTANCE = Mappers.getMapper(AboStudentUnternehmenMapper.class);

    AboStudentUnternehmen aboStudentUnternehmenDTOToAboStudentUnternehmen(AboDTO aboDTO);
    AboDTO aboStudentUnternehmenToaboStudentUnternehmenDTO(AboStudentUnternehmen aboStudentUnternehmen);
}
