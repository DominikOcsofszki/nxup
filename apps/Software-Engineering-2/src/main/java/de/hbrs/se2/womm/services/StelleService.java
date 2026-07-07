package de.hbrs.se2.womm.services;

import de.hbrs.se2.womm.dtos.StelleDTO;
import de.hbrs.se2.womm.entities.Stelle;
import de.hbrs.se2.womm.mapper.StelleMapper;
import de.hbrs.se2.womm.repositories.StelleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StelleService {
    private final StelleRepository stelleRepository;
    private final StelleMapper stelleMapper = StelleMapper.INSTANCE;

    public StelleService(StelleRepository stelleRepository) {
        this.stelleRepository = stelleRepository;
    }

    public List<StelleDTO> getByUnternehmenId(Long unternehmenId) { //ToDo why not working ????
        return stelleRepository.findByUnternehmen_UnternehmenId(unternehmenId)
                .stream()
                .map(stelleMapper::stelleToStelleDto)
                .toList();
    }

    public List<StelleDTO> getByNutzerId(Long nutzerID) { //ToDo why not working ????
        return stelleRepository.findByUnternehmen_Nutzer_NutzerId(nutzerID)
                .stream()
                .map(stelleMapper::stelleToStelleDto)
                .toList();
    }

    public Optional<StelleDTO> getById(Long id) {
        return stelleRepository.findById(id)
                .map(stelleMapper::stelleToStelleDto);
    }

    public StelleDTO saveStelle(StelleDTO stelleDTO) {
        Stelle stelle = stelleMapper.stelleDtoToStelle(stelleDTO);
        Stelle erzeugteStelle = stelleRepository.save(stelle);
        return stelleMapper.stelleToStelleDto(erzeugteStelle);
    }

    public List<StelleDTO> getAll() {
        return stelleRepository.findAll()
                .stream()
                .map(stelleMapper::stelleToStelleDto)
                .toList();
    }

    public List<StelleDTO> getAllByFilter(String filter, String attribute) {
        attribute = attribute.toLowerCase();
        List<Stelle> result;

        switch (attribute) {
            case "titel":
                result = stelleRepository.findByStelleTitelIsContainingIgnoreCase(filter);
                break;
            case "ort":
                result = stelleRepository.findByStelleOrtIsContainingIgnoreCase(filter);
                break;
            case "beschreibung":
                result = stelleRepository.findByStelleBeschreibungIsContainingIgnoreCase(filter);
                break;
            case "website":
                result = stelleRepository.findByStelleWebsiteIsContainingIgnoreCase(filter);
                break;
            case "unternehmen":
                result = stelleRepository.findByUnternehmen_NameIsContainingIgnoreCase(filter);
                break;
            default:
                result = stelleRepository.findAll();
        }
        return result.stream()
                .map(stelleMapper::stelleToStelleDto)
                .toList();
    }
}
