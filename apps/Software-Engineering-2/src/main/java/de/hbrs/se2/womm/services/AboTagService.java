package de.hbrs.se2.womm.services;

import de.hbrs.se2.womm.dtos.AboTagDTO;
import de.hbrs.se2.womm.entities.AboTag;
import de.hbrs.se2.womm.mapper.AboTagMapper;
import de.hbrs.se2.womm.mapper.UnternehmenMapper;
import de.hbrs.se2.womm.repositories.AboTagRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AboTagService {
    AboTagMapper aboTagMapper = AboTagMapper.INSTANCE;
    AboTagRepository aboTagRepository;

    public AboTagService(AboTagRepository aboTagRepository){
        this.aboTagRepository = aboTagRepository;
    }

    public AboTagDTO getById(long id){
        return aboTagRepository.findById(id)
                .map(aboTagMapper::aboTagToDto)
                .orElse(null);
    }

    public List<AboTagDTO> getByNutzerId(long id){
        return aboTagRepository.findAboTagByStudent_Nutzer_NutzerId(id)
                .stream()
                .map(aboTagMapper::aboTagToDto)
                .toList();
    }

    public void saveAboTag(AboTagDTO aboTagDTO){
        AboTag zuSpeichern = aboTagMapper.aboTagDtoToAboTag(aboTagDTO);
        aboTagRepository.save(zuSpeichern);
    }
}
