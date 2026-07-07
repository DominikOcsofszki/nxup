package de.hbrs.se2.womm.junit.services;

import de.hbrs.se2.womm.dtos.AboTagDTO;
import de.hbrs.se2.womm.dtos.TagDto;
import de.hbrs.se2.womm.entities.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

class AboTagDTOTest {

    @Mock
    private Student student;

    @Mock
    private TagDto tagDto;

    @InjectMocks
    private AboTagDTO aboTagDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        aboTagDTO = AboTagDTO.builder()
                .aboId(1)
                .aboBenachrichtigung(true)
                .student(student)
                .tag(tagDto)
                .build();
    }

    @Test
    void testAboTagDTOCreation() {
        when(student.getStudentName()).thenReturn("Max Mustermann");
        when(tagDto.getTagText()).thenReturn("Java");

        assertNotNull(aboTagDTO);
        assertEquals(1, aboTagDTO.getAboId());
        assertEquals(true, aboTagDTO.getAboBenachrichtigung());
        assertEquals(student, aboTagDTO.getStudent());
        assertEquals(tagDto, aboTagDTO.getTag());

        assertEquals("Max Mustermann", aboTagDTO.getStudent().getStudentName());
        assertEquals("Java", aboTagDTO.getTag().getTagText());
    }

}

