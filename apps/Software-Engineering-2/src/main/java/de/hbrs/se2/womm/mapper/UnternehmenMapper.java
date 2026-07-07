package de.hbrs.se2.womm.mapper;

import de.hbrs.se2.womm.dtos.UnternehmenDTO;
import de.hbrs.se2.womm.entities.Unternehmen;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper()
public interface UnternehmenMapper {
    UnternehmenMapper INSTANCE = Mappers.getMapper(UnternehmenMapper.class);

    UnternehmenDTO unternehmenZuDTO(Unternehmen unternehmen);
    Unternehmen dtoZuUnternehmen(UnternehmenDTO unternehmenDTO);

}
