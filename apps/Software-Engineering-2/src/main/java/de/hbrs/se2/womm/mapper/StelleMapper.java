package de.hbrs.se2.womm.mapper;

import de.hbrs.se2.womm.dtos.StelleDTO;
import de.hbrs.se2.womm.entities.Stelle;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface StelleMapper {
    StelleMapper INSTANCE = Mappers.getMapper(StelleMapper.class);

    StelleDTO stelleToStelleDto(Stelle stelle);

    Stelle stelleDtoToStelle(StelleDTO stelleDTO);
}
