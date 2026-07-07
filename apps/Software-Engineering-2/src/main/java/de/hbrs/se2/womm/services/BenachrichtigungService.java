package de.hbrs.se2.womm.services;

import de.hbrs.se2.womm.dtos.BenachrichtigungDTO;
import de.hbrs.se2.womm.entities.Benachrichtigung;
import de.hbrs.se2.womm.mapper.BenachrichtigungMapper;
import de.hbrs.se2.womm.repositories.BenachrichtigungRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BenachrichtigungService {
    BenachrichtigungMapper benachrichtigungMapper = BenachrichtigungMapper.INSTANCE;
    BenachrichtigungRepository benachrichtigungRepository;

    public BenachrichtigungService(BenachrichtigungRepository benachrichtigungRepository) {
        this.benachrichtigungRepository = benachrichtigungRepository;
    }

    public List<BenachrichtigungDTO> getByNutzerId(Long nutzer_id){
        return benachrichtigungRepository.findBenachrichtigungByNutzer_NutzerId(nutzer_id)
                .stream()
                .map(benachrichtigungMapper::BenachrichtigungToDto)
                .toList();
    }

    public List<BenachrichtigungDTO> getUngelesenByNutzerID(Long nutzer_id){
        return benachrichtigungRepository.findBenachrichtigungByNutzer_NutzerIdAndGelesenIsFalse(nutzer_id)
                .stream()
                .map(benachrichtigungMapper::BenachrichtigungToDto)
                .toList();
    }

    public int getAnzahlUngelesenByNutzerID(Long nutzer_id){
        return benachrichtigungRepository.findBenachrichtigungByNutzer_NutzerIdAndGelesenIsFalse(nutzer_id).size();
    }

    public void setGelesen(BenachrichtigungDTO benachrichtigungDTO){
        benachrichtigungDTO.setGelesen(true);
        Benachrichtigung benachrichtigung = benachrichtigungMapper
                .BenachrichtigungDTOToBenachrichtigung(benachrichtigungDTO);
        benachrichtigungRepository.save(benachrichtigung);
    }

    public void saveBenachrichtigung(BenachrichtigungDTO benachrichtigungDTO){
        Benachrichtigung benachrichtigung = benachrichtigungMapper
                .BenachrichtigungDTOToBenachrichtigung(benachrichtigungDTO);
        benachrichtigungRepository.save(benachrichtigung);
    }
}
