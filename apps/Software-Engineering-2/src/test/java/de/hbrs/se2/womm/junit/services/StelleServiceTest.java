package de.hbrs.se2.womm.junit.services;

import de.hbrs.se2.womm.dtos.StelleDTO;
import de.hbrs.se2.womm.dtos.UnternehmenDTO;
import de.hbrs.se2.womm.entities.Nutzer;
import de.hbrs.se2.womm.entities.Stelle;
import de.hbrs.se2.womm.entities.Unternehmen;
import de.hbrs.se2.womm.repositories.StelleRepository;
import de.hbrs.se2.womm.services.StelleService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class StelleServiceTest {

    private StelleRepository repo;

    private StelleService service;

    private List<StelleDTO> listDTO;
    private List<Stelle> listEntity;

    private StelleDTO stelleDTO;

    private Stelle stelle;

    @BeforeEach
    void setup() {
        repo = mock(StelleRepository.class);
        service = new StelleService(repo);
        listDTO = new ArrayList<>();
        listEntity = new ArrayList<>();
        stelleDTO = StelleDTO.builder()
                .stelleId(100L)
                .stelleTitel("titel")
                .stelleOrt("ort")
                .stelleBeschreibung("beschreibung")
                .stelleWebsite("url")
                .unternehmen(null)
                .build();

        stelle = Stelle.builder()
                .stelleId(100)
                .stelleTitel("titel")
                .stelleOrt("ort")
                .stelleBeschreibung("beschreibung")
                .stelleWebsite("url")
                .unternehmen(null)
                .build();

        when(repo.findAll()).thenReturn(listEntity);
    }

    @AfterEach
    void teardown() {
        repo = null;
        service = null;
        listDTO = null;
        listEntity = null;
        stelleDTO = null;
        stelle = null;
    }

    @Test
    void testGetByUnternehmenId() {
        when(repo.findByUnternehmen_UnternehmenId(any(Long.class))).thenAnswer(
                i -> {
                    return listEntity
                            .stream()
                            .filter(p -> (long) p.getUnternehmen().getUnternehmenId() == (long) i.getArgument(0))
                            .toList();
                }
        );

        List<StelleDTO> actual = service.getByUnternehmenId(101L);
        assertNotNull(actual);
        assertEquals(0, actual.size());

        listEntity.add(Stelle.builder()
                .stelleId(100)
                .stelleTitel("titel")
                .stelleOrt("ort")
                .stelleBeschreibung("beschreibung")
                .stelleWebsite("url")
                .unternehmen(Unternehmen.builder().unternehmenId(101).name("unternehmen").build())
                .build());

        actual = service.getByUnternehmenId(101L);
        assertEquals(1, actual.size());

        listEntity.add(Stelle.builder()
                .stelleId(999)
                .stelleTitel("titellll")
                .stelleOrt("ortttt")
                .stelleBeschreibung("beschreibunggggggg")
                .stelleWebsite("urlllllll")
                .unternehmen(Unternehmen.builder().unternehmenId(997).name("unternehmennnnnnnn").build())
                .build());

        actual = service.getByUnternehmenId(101L);
        assertEquals(1, actual.size());

        listEntity.add(Stelle.builder()
                .unternehmen(Unternehmen.builder().unternehmenId(101).build())
                .build());

        actual = service.getByUnternehmenId(101L);
        assertEquals(2, actual.size());
    }

    @Test
    void testGetByNutzerId() {
        when(repo.findByUnternehmen_Nutzer_NutzerId(any(Long.class))).thenAnswer(
                i -> {
                    return listEntity
                            .stream()
                            .filter(p -> (long) p.getUnternehmen().getNutzer().getNutzerId() == (long) i.getArgument(0))
                            .toList();
                }
        );

        List<StelleDTO> actual = service.getByNutzerId(102L);
        assertNotNull(actual);
        assertEquals(0, actual.size());

        Nutzer nutzer = Nutzer.builder().nutzerId(102L).build();

        listEntity.add(Stelle.builder()
                .unternehmen(
                        Unternehmen.builder().nutzer(nutzer).build()
                )
                .build());


        actual = service.getByNutzerId(102L);
        assertEquals(1, actual.size());

        listEntity.add(Stelle.builder()
                .unternehmen(
                        Unternehmen.builder().nutzer(
                                Nutzer.builder().nutzerId(999L).build()
                        ).build()
                )
                .build());

        actual = service.getByNutzerId(102L);
        assertEquals(1, actual.size());

        listEntity.add(Stelle.builder()
                .unternehmen(
                        Unternehmen.builder().nutzer(nutzer).build()
                )
                .build());

        actual = service.getByNutzerId(102L);
        assertEquals(2, actual.size());
    }

    @Test
    void testGetAll() {

        List<StelleDTO> actual = service.getAll();
        assertNotNull(actual);
        assertEquals(listDTO.size(), actual.size());

        listEntity.add(stelle);
        listDTO.add(stelleDTO);

        actual = service.getAll();
        assertNotNull(actual);
        assertEquals(listDTO.size(), actual.size());

        assertTrue(listDTO.get(0).equals(actual.get(0)));

    }

    @Test
    void testGetAllByFilter() {
        //--------Mocking----------
        when(repo.findByStelleTitelIsContainingIgnoreCase(any(String.class))).thenAnswer(
                i -> {
                    return listEntity
                            .stream()
                            .filter(p -> containsIgnoreCase(p.getStelleTitel(), i.getArgument(0).toString()))
                            .toList();
                }
        );
        when(repo.findByStelleOrtIsContainingIgnoreCase(any(String.class))).thenAnswer(
                i -> {
                    return listEntity
                            .stream()
                            .filter(p -> containsIgnoreCase(p.getStelleOrt(), i.getArgument(0).toString()))
                            .toList();
                }
        );
        when(repo.findByStelleBeschreibungIsContainingIgnoreCase(any(String.class))).thenAnswer(
                i -> {
                    return listEntity
                            .stream()
                            .filter(p -> containsIgnoreCase(p.getStelleBeschreibung(), i.getArgument(0).toString()))
                            .toList();
                }
        );
        when(repo.findByStelleWebsiteIsContainingIgnoreCase(any(String.class))).thenAnswer(
                i -> {
                    return listEntity
                            .stream()
                            .filter(p -> containsIgnoreCase(p.getStelleWebsite(), i.getArgument(0).toString()))
                            .toList();
                }
        );
        when(repo.findByUnternehmen_NameIsContainingIgnoreCase(any(String.class))).thenAnswer(
                i -> {
                    return listEntity
                            .stream()
                            .filter(p -> containsIgnoreCase(p.getUnternehmen().getName(), i.getArgument(0).toString()))
                            .toList();
                }
        );
        //--------Testing----------
        listDTO = service.getAllByFilter("2023", "Geburtsjahr");
        assertEquals(0, listDTO.size());

        listEntity.add(Stelle.builder()
                .stelleId(100)
                .stelleTitel("titel")
                .stelleOrt("ort")
                .stelleBeschreibung("beschreibung")
                .stelleWebsite("url")
                .unternehmen(Unternehmen.builder().unternehmenId(101).name("unternehmen").build())
                .build());
        testServiceGetAllByFilter("2023", "Geburtsjahr", 1);

        testServiceGetAllByFilter("ite", "titel", 1);

        testServiceGetAllByFilter("le", "titel", 0);

        testServiceGetAllByFilter("or", "ort", 1);

        testServiceGetAllByFilter("Oettershagen", "ort", 0);

        testServiceGetAllByFilter("beschrei", "beschreibung", 1);

        testServiceGetAllByFilter("descr", "beschreibung", 0);

        testServiceGetAllByFilter("url", "website", 1);

        testServiceGetAllByFilter("seite", "website", 0);

        testServiceGetAllByFilter("unter", "unternehmen", 1);

        testServiceGetAllByFilter("le", "unternehmen", 0);
    }

    private void testServiceGetAllByFilter(String filter, String attribute, int expectedSize) {
        listDTO = service.getAllByFilter(filter, attribute);
        assertEquals(expectedSize, listDTO.size());
    }

    @Test
    void testGetById() {
        when(repo.findById(any(Long.class)))
                .thenAnswer(
                        i -> {
                            List<Stelle> curr = listEntity
                                    .stream()
                                    .filter(p -> p.getStelleId().toString().equals(i.getArgument(0).toString()))
                                    .toList();
                            return curr.size() == 0 ? Optional.empty() : Optional.of(curr.get(0));
                        }
                );

        Optional<StelleDTO> actual = service.getById(100L);
        assertTrue(actual.isEmpty());

        listEntity.add(stelle);

        actual = service.getById(100L);
        assertFalse(actual.isEmpty());
        assertEquals(stelleDTO.getStelleId(), actual.get().getStelleId());

        Stelle stelle_2 = Stelle.builder()
                .stelleId(999)
                .stelleTitel("titel")
                .stelleOrt("ort")
                .stelleBeschreibung("beschreibung")
                .stelleWebsite("url")
                .unternehmen(null)
                .build();

        listEntity.add(stelle_2);

        actual = service.getById(999L);
        assertFalse(actual.isEmpty());
        assertEquals((long) stelle_2.getStelleId(), actual.get().getStelleId());
    }

    @Test
    void testSaveStelle() {
        when(repo.save(any(Stelle.class))).thenAnswer(i -> {
            Stelle input = (Stelle) i.getArgument(0);
            listEntity.add(input);
            return input;
        });

        stelle = Stelle.builder()
                .stelleId(100)
                .stelleTitel("titel")
                .stelleOrt("ort")
                .stelleBeschreibung("beschreibung")
                .stelleWebsite("url")
                .unternehmen(Unternehmen.builder().name("unternehmen").build())
                .build();

        stelleDTO = StelleDTO.builder()
                .stelleId(100L)
                .stelleTitel("titel")
                .stelleOrt("ort")
                .stelleBeschreibung("beschreibung")
                .stelleWebsite("url")
                .unternehmen(UnternehmenDTO.builder().name("unternehmen").build())
                .build();

        assertEquals(0, listEntity.size());

        service.saveStelle(stelleDTO);
        assertEquals(1, listEntity.size());

        stelle = listEntity.get(0);
        assertEquals(stelleDTO.getStelleId(), (long) stelle.getStelleId());
        assertEquals(stelleDTO.getStelleTitel(), stelle.getStelleTitel());
        assertEquals(stelleDTO.getUnternehmen().getName(), stelle.getUnternehmen().getName());
        assertEquals(stelleDTO.getStelleOrt(), stelle.getStelleOrt());
        assertEquals(stelleDTO.getStelleWebsite(), stelle.getStelleWebsite());
        assertEquals(stelleDTO.getStelleBeschreibung(), stelle.getStelleBeschreibung());

    }

    private boolean containsIgnoreCase(String base, String search) {
        return base.toLowerCase().contains(search.toLowerCase());
    }
}
