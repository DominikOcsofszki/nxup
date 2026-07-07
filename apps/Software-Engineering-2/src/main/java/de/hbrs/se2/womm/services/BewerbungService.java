package de.hbrs.se2.womm.services;

import de.hbrs.se2.womm.dtos.BewerbungDTO;
import de.hbrs.se2.womm.entities.Bewerbung;
import de.hbrs.se2.womm.mapper.BewerbungMapper;
import de.hbrs.se2.womm.repositories.BewerbungRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BewerbungService {
    BewerbungRepository bewerbungRepository;
    BewerbungMapper bewerbungMapper = BewerbungMapper.INSTNACE;

    public BewerbungService(BewerbungRepository bewerbungRepository) {
        this.bewerbungRepository = bewerbungRepository;
    }

    public List<BewerbungDTO> getAll() {
        return bewerbungRepository.findAll()
                .stream()
                .map(bewerbungMapper::bewerbungToBewerbungDto)
                .toList();
    }

    public Optional<BewerbungDTO> getById(Long id) {
        return bewerbungRepository.findById(id)
                .map(bewerbungMapper::bewerbungToBewerbungDto);
    }

    public List<BewerbungDTO> getByStudentNutzerId(Long id){
        return bewerbungRepository.findBewerbungByStudent_Nutzer_NutzerId(id)
                .stream()
                .map(bewerbungMapper::bewerbungToBewerbungDto).toList();
    }

    public BewerbungDTO saveBewerbung(BewerbungDTO bewerbungDTO) {
        Bewerbung bewerbung = BewerbungMapper.INSTNACE.bewerbungDtoToBewerbung(bewerbungDTO);
        Bewerbung erzeugteBewerbung = bewerbungRepository.save(bewerbung);
        return BewerbungMapper.INSTNACE.bewerbungToBewerbungDto(erzeugteBewerbung);
    }

    public List<BewerbungDTO> getByUnternehmenNutzerId(Long id) {
        return bewerbungRepository.findBewerbungByUnternehmen_UnternehmenId(id)
                .stream()
                .map(bewerbungMapper::bewerbungToBewerbungDto).toList();
    }
}
