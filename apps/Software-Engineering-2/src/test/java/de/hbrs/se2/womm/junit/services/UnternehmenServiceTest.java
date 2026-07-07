package de.hbrs.se2.womm.junit.services;

import de.hbrs.se2.womm.dtos.NutzerDTO;
import de.hbrs.se2.womm.dtos.UnternehmenDTO;
import de.hbrs.se2.womm.entities.Nutzer;
import de.hbrs.se2.womm.entities.Unternehmen;
import de.hbrs.se2.womm.mapper.NutzerMapper;
import de.hbrs.se2.womm.mapper.UnternehmenMapper;
import de.hbrs.se2.womm.repositories.NutzerRepository;
import de.hbrs.se2.womm.repositories.UnternehmenRepository;
import de.hbrs.se2.womm.services.UnternehmenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class UnternehmenServiceTest {

    private UnternehmenService unternehmenService;
    private UnternehmenRepository unternehmenRepositoryMock;
    private NutzerRepository nutzerRepositoryMock;
    private UnternehmenMapper unternehmenMapper;
    private NutzerMapper nutzerMapper;

    private UnternehmenDTO unternehmenDTO;
    private Unternehmen unternehmen;

    @BeforeEach
    void setUp() {
        unternehmenRepositoryMock = mock(UnternehmenRepository.class);
        nutzerRepositoryMock = mock(NutzerRepository.class);
        unternehmenMapper = UnternehmenMapper.INSTANCE;
        nutzerMapper = NutzerMapper.INSTANCE;

        unternehmenService = new UnternehmenService(unternehmenRepositoryMock, nutzerRepositoryMock);

        unternehmenDTO = UnternehmenDTO.builder()
                .unternehmenId(1L).build();

        unternehmen = Unternehmen.builder()
                .unternehmenId(1).build();
    }

    @Test
    void testGetUnternehmenPerID() {
        long id = 1L;
        when(unternehmenRepositoryMock.findUnternehmenByUnternehmenId(id)).thenReturn(Optional.of(unternehmen));
        UnternehmenDTO result = unternehmenService.getUnternehmenPerID(id);
        assertNotNull(result);
        assertEquals(unternehmenDTO.getUnternehmenId(), result.getUnternehmenId());
    }

    @Test
    void testGetByNutzerId() {
        long nutzerId = 1L;
        when(unternehmenRepositoryMock.findUnternehmenByNutzer_NutzerId(nutzerId)).thenReturn(Optional.of(unternehmen));
        UnternehmenDTO result = unternehmenService.getByNutzerId(nutzerId);
        assertNotNull(result);
        assertEquals(1L, result.getUnternehmenId());
    }

    @Test
    void testGetUnternehmenDTOPerName() {
        String unternehmenName = "CompanyA";
        Unternehmen unternehmen1 = Unternehmen.builder()
                .unternehmenId(1).build();

        Unternehmen unternehmen2 = Unternehmen.builder()
                .unternehmenId(2).build();

        when(unternehmenRepositoryMock.findUnternehmenByNameIgnoreCaseContaining(unternehmenName))
                .thenReturn(Arrays.asList(unternehmen1, unternehmen2));

        List<UnternehmenDTO> result = unternehmenService.getUnternehmenDTOPerName(unternehmenName);
        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void testGetAll() {
        Unternehmen unternehmen1 = Unternehmen.builder()
                .unternehmenId(1).build();

        Unternehmen unternehmen2 = Unternehmen.builder()
                .unternehmenId(2).build();

        when(unternehmenRepositoryMock.findAll()).thenReturn(Arrays.asList(unternehmen1, unternehmen2));

        List<UnternehmenDTO> result = unternehmenService.getAll();
        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void testSaveUnternehmen() {
        NutzerDTO nutzerDTO = NutzerDTO.builder()
                .nutzerId(1L)
                // weitere Nutzer-Details setzen, falls erforderlich
                .build();

        UnternehmenDTO unternehmenDTO = UnternehmenDTO.builder()
                .unternehmenId(1L)
                .nutzer(nutzerDTO)
                // weitere Unternehmens-Details setzen, falls erforderlich
                .build();

        Nutzer nutzer = nutzerMapper.nutzerDTOToNutzer(nutzerDTO);
        Unternehmen unternehmen = unternehmenMapper.dtoZuUnternehmen(unternehmenDTO);

        when(nutzerRepositoryMock.save(any(Nutzer.class))).thenReturn(nutzer);
        when(unternehmenRepositoryMock.save(any(Unternehmen.class))).thenReturn(unternehmen);

        unternehmenService.saveUnternehmen(unternehmenDTO);

        verify(nutzerRepositoryMock, times(1)).save(any(Nutzer.class));
        verify(unternehmenRepositoryMock, times(1)).save(any(Unternehmen.class));
    }

}
