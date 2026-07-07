package de.hbrs.se2.womm.junit.services;

import de.hbrs.se2.womm.dtos.AboDTO;
import de.hbrs.se2.womm.entities.AboStudentUnternehmen;
import de.hbrs.se2.womm.mapper.AboStudentUnternehmenMapper;
import de.hbrs.se2.womm.repositories.AboStudentUnternehmenRepository;
import de.hbrs.se2.womm.services.AboStudentUnternehmenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class AboStudentUnternehmenServiceTest {

    @Mock
    private AboStudentUnternehmenRepository aboStudentUnternehmenRepository;

    @Mock
    private AboStudentUnternehmenMapper aboStudentUnternehmenMapper;

    @InjectMocks
    private AboStudentUnternehmenService aboStudentUnternehmenService;

    private AboStudentUnternehmen aboStudentUnternehmen;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        aboStudentUnternehmen = AboStudentUnternehmen.builder()
                .aboId(1)
                .student(null)
                .unternehmen(null)
                .build();
    }

    @Test
    void testGetById() {
        long id = 1;
        AboDTO mockedDTO = AboDTO.builder().aboId(1).build();
        when(aboStudentUnternehmenRepository.findById(id)).thenReturn(Optional.of(aboStudentUnternehmen));
        when(aboStudentUnternehmenMapper.aboStudentUnternehmenToaboStudentUnternehmenDTO(aboStudentUnternehmen)).thenReturn(mockedDTO);

        Optional<AboDTO> result = Optional.ofNullable(aboStudentUnternehmenService.getById(id));
        assertTrue(result.isPresent());
    }

    @Test
    void testGetByNutzerId() {
        long nutzerId = 1;
        AboDTO mockedDTO = AboDTO.builder().aboId(1).build();

        when(aboStudentUnternehmenRepository.findByStudent_NutzerNutzerIdOrUnternehmen_Nutzer_NutzerId(nutzerId, nutzerId))
                .thenReturn(Arrays.asList(aboStudentUnternehmen));
        when(aboStudentUnternehmenMapper.aboStudentUnternehmenToaboStudentUnternehmenDTO(aboStudentUnternehmen)).thenReturn(mockedDTO);

        List<AboDTO> result = aboStudentUnternehmenService.getByNutzerId(nutzerId);
        assertNotNull(result);
    }

    @Test
    void testGetAll() {
        AboStudentUnternehmen abo1 = AboStudentUnternehmen.builder()
                .aboId(1).build();

        AboStudentUnternehmen abo2 = AboStudentUnternehmen.builder()
                .aboId(2).build();

        AboDTO mockedDTO1 = AboDTO.builder()
                .aboId(1).build();

        AboDTO mockedDTO2 = AboDTO.builder()
                .aboId(2).build();

        when(aboStudentUnternehmenRepository.findAll()).thenReturn(Arrays.asList(abo1, abo2));
        when(aboStudentUnternehmenMapper.aboStudentUnternehmenToaboStudentUnternehmenDTO(abo1)).thenReturn(mockedDTO1);
        when(aboStudentUnternehmenMapper.aboStudentUnternehmenToaboStudentUnternehmenDTO(abo2)).thenReturn(mockedDTO2);

        List<AboDTO> result = aboStudentUnternehmenService.getAll();
        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void testSaveAboStudentUnternehmen() {
        AboDTO aboDTO = AboDTO.builder()
                .aboId(1).build();

        AboStudentUnternehmen aboToSave = AboStudentUnternehmen.builder()
                .aboId(1).build();

        when(aboStudentUnternehmenMapper.aboStudentUnternehmenDTOToAboStudentUnternehmen(aboDTO)).thenReturn(aboToSave);

        aboStudentUnternehmenService.saveAboStudentUnternehmen(aboDTO);
    }
}
