package de.hbrs.se2.womm.services;

import de.hbrs.se2.womm.dtos.AboDTO;
import de.hbrs.se2.womm.entities.AboStudentUnternehmen;
import de.hbrs.se2.womm.mapper.AboStudentUnternehmenMapper;
import de.hbrs.se2.womm.repositories.AboStudentUnternehmenRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AboStudentUnternehmenService {
    AboStudentUnternehmenRepository aboStudentUnternehmenRepository;
    AboStudentUnternehmenMapper aboStudentUnternehmenMapper = AboStudentUnternehmenMapper.INSTANCE;

    public AboStudentUnternehmenService(AboStudentUnternehmenRepository aboStudentUnternehmenRepository){
        this.aboStudentUnternehmenRepository =  aboStudentUnternehmenRepository;
    }

    public AboDTO getById(long id){
        return aboStudentUnternehmenRepository
                .findById(id)
                .map(aboStudentUnternehmenMapper::aboStudentUnternehmenToaboStudentUnternehmenDTO)
                .orElse(null);
    }

    public List<AboDTO> getByNutzerId(long id){
        return aboStudentUnternehmenRepository.findByStudent_NutzerNutzerIdOrUnternehmen_Nutzer_NutzerId(id,id)
                .stream()
                .map(aboStudentUnternehmenMapper::aboStudentUnternehmenToaboStudentUnternehmenDTO)
                .toList();
    }

    public List<AboDTO> getByUnternehmenId(long unternehmen_id){
        return aboStudentUnternehmenRepository.findByUnternehmen_UnternehmenId(unternehmen_id)
                .stream()
                .map(aboStudentUnternehmenMapper::aboStudentUnternehmenToaboStudentUnternehmenDTO)
                .toList();
    }

    public List<AboDTO> getAll(){
        return aboStudentUnternehmenRepository.findAll()
                .stream()
                .map(aboStudentUnternehmenMapper::aboStudentUnternehmenToaboStudentUnternehmenDTO)
                .toList();
    }

    public void saveAboStudentUnternehmen(AboDTO aboDTO){
        AboStudentUnternehmen zuSpeichern =
                aboStudentUnternehmenMapper.aboStudentUnternehmenDTOToAboStudentUnternehmen(aboDTO);
        aboStudentUnternehmenRepository.save(zuSpeichern);
    }
}
