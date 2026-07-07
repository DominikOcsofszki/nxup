package de.hbrs.se2.womm.dtos;

import de.hbrs.se2.womm.entities.Student;
import de.hbrs.se2.womm.entities.Tag;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AboTagDTO {
    private Integer aboId;
    private Boolean aboBenachrichtigung;
    private Student student;
    private TagDto tag;
}
