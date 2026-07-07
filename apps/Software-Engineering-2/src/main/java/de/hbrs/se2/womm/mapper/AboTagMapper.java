package de.hbrs.se2.womm.mapper;

import de.hbrs.se2.womm.dtos.AboTagDTO;
import de.hbrs.se2.womm.entities.AboTag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AboTagMapper {
    AboTagMapper INSTANCE = Mappers.getMapper(AboTagMapper.class);

    AboTag aboTagDtoToAboTag(AboTagDTO aboTag);
    AboTagDTO aboTagToDto(AboTag aboTag);

}
