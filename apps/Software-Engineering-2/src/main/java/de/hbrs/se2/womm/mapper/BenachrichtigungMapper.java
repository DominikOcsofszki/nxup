package de.hbrs.se2.womm.mapper;

import de.hbrs.se2.womm.dtos.BenachrichtigungDTO;
import de.hbrs.se2.womm.entities.Benachrichtigung;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BenachrichtigungMapper {
    BenachrichtigungMapper INSTANCE = Mappers.getMapper(BenachrichtigungMapper.class);


    Benachrichtigung BenachrichtigungDTOToBenachrichtigung(BenachrichtigungDTO Benachrichtigung);
    BenachrichtigungDTO BenachrichtigungToDto(Benachrichtigung Benachrichtigung);
}
