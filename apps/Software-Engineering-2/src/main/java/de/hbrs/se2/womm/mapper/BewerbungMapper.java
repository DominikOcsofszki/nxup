package de.hbrs.se2.womm.mapper;

import de.hbrs.se2.womm.dtos.BewerbungDTO;
import de.hbrs.se2.womm.entities.Bewerbung;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BewerbungMapper {
    BewerbungMapper INSTNACE = Mappers.getMapper(BewerbungMapper.class);


    @Mapping(target = "student", source = "bewerbungStudent")
    @Mapping(target = "stelle", source =     "bewerbungStelle")
    @Mapping(target = "unternehmen", source = "bewerbungUnternehmen")
    Bewerbung bewerbungDtoToBewerbung(BewerbungDTO bewerbungDTO);

    @Mapping(target = "bewerbungStudent", source = "student")
    @Mapping(target = "bewerbungStelle", source = "stelle")
    @Mapping(target = "bewerbungUnternehmen", source = "unternehmen")
    BewerbungDTO bewerbungToBewerbungDto(Bewerbung bewerbung);
}
